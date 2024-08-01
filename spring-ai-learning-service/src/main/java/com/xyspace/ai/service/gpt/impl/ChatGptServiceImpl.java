package com.xyspace.ai.service.gpt.impl;

import com.xyspace.ai.service.gpt.ChatGptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatGptServiceImpl implements ChatGptService {

    @Autowired
    @Qualifier("openAiChatClient")
    private final ChatClient openAiChatClient;

    @Autowired
    public ChatGptServiceImpl(ChatClient chatClient) {
        this.openAiChatClient = chatClient;
    }

    @Override
    public String completion(String message) {

        return openAiChatClient.call(message);
    }
}
