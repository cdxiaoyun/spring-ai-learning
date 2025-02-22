package com.xyspace.ai.service.tongyi.impl;

import com.alibaba.cloud.ai.tongyi.chat.TongYiChatClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.StreamingChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service("tongYiSimpleServiceImpl")
@Slf4j
public class TongYiSimpleServiceImpl extends AbstractTongYiServiceImpl {
    /**
     * 自动注入ChatClient、StreamingChatClient，屏蔽模型调用细节
     */
    @Autowired
    @Qualifier("tongYiChatClient")
    private ChatClient tongYiChatClient;

    /**
     * 具体实现：
     */
    @Override
    public String completion(String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return tongYiChatClient.call(prompt).getResult().getOutput().getContent();
    }
}