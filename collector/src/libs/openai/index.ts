import OpenAI from 'openai';

export const openai: OpenAI = new OpenAI({
  apiKey:
    process.env['OPENAI_API_KEY'] ??
    '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',
});
