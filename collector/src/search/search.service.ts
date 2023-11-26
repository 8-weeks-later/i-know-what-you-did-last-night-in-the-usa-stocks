import { Injectable } from '@nestjs/common';
import { openai } from '../libs/openai';
import { ChatCompletion } from 'openai/src/resources/chat/completions';
import wretch from 'wretch';
import QueryStringAddon from 'wretch/addons/queryString';
import * as cheerio from 'cheerio';
import { XMLParser } from 'fast-xml-parser';

@Injectable()
export class SearchService {
  private readonly parser = new XMLParser();
  constructor() {}
  async search(stock: string): Promise<any> {
    const response = wretch(
      `https://news.google.com/rss/search?q=intitle:${stock}+when:8h&hl=en-US&gl=US&ceid=US:en&sort=date`,
    ).get();

    const text = await response.text();

    const news: { id: string; title: string; link: string }[] = this.parser
      .parse(text)
      .rss.channel.item.map(({ guid, title, link }) => {
        return { id: guid, title, link };
      });

    const titles = news
      .slice(0, 20)
      .map(({ title }, index) => `${index}: ${title}`)
      .join('\n');

    const topicSystemPrompt = `You are an AI assistant that guesses the topic of the news.
    You need to look at the title of a given news, guess the topic in a sentence, and return the result in json format.
    '''json
    [{
      id: number,
      title:"title",
      topic:"topic",
    }]
    '''`;

    const topicResult = await openai.chat.completions.create({
      messages: [
        { role: 'system', content: topicSystemPrompt },
        { role: 'user', content: titles },
      ],
      model: 'gpt-3.5-turbo',
      max_tokens: 1500,
      temperature: 0.7,
      top_p: 1,
    });
    const topics = topicResult.choices[0].message.content;

    const sortedTopicResult = await openai.chat.completions.create({
      messages: [
        {
          role: 'system',
          content: `Guess the relationship with the stock price, assign a score out of 100, and sort by score. Returns results in json format.
            '''json
            [{
              id: number,
              title:"",
              topic:"",
              score:number,
            }]
            '''
            `,
        },
        { role: 'user', content: topics },
      ],
      model: 'gpt-3.5-turbo',
      max_tokens: 1500,
      temperature: 0.7,
      top_p: 1,
    });
    console.log(sortedTopicResult.choices[0].message.content);
    const sortedTopics = sortedTopicResult.choices[0].message.content;

    const summaryResult = await openai.chat.completions.create({
      messages: [
        {
          role: 'system',
          content: `Using the given article, similar topics should be grouped and summarized in the following order.

          1. Group articles with similar topics.
          2. Summarize the contents using the topics of grouped articles.
          3. Returns the results in json format, including grouped ids and summary sentences together in the results.
      
          '''json
          [{
            id:number,
            articleIds:[],
            summary:"",
          }]
          '''`,
        },
        { role: 'user', content: sortedTopics },
      ],
      model: 'gpt-3.5-turbo',
      max_tokens: 1500,
      temperature: 0.7,
      top_p: 1,
    });

    console.log(summaryResult.choices[0].message.content);
    return summaryResult.choices[0].message.content;
  }
}

(async () => {
  const s = new SearchService();
  const res = await s.search('nvidia');
  console.log(res);
})();
