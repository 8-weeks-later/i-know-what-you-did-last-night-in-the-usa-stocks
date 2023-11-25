import { Injectable } from '@nestjs/common';
import { openai } from '../libs/openai';
import { ChatCompletion } from 'openai/src/resources/chat/completions';

@Injectable()
export class SearchService {
  async search(stock: string): Promise<ChatCompletion> {
    return openai.chat.completions.create({
      messages: [{ role: 'user', content: 'Say this is a test' }],
      model: 'gpt-3.5-turbo',
    });
  }
}
