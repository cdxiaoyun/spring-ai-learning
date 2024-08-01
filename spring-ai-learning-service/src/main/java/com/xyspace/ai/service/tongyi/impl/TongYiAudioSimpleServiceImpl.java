package com.xyspace.ai.service.tongyi.impl;

import com.alibaba.cloud.ai.tongyi.audio.api.SpeechClient;
import com.alibaba.cloud.ai.tongyi.audio.api.SpeechPrompt;
import com.alibaba.dashscope.audio.tts.SpeechSynthesisAudioFormat;
import com.xyspace.ai.service.tongyi.TongYiService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

@Slf4j
@Service
public class TongYiAudioSimpleServiceImpl extends AbstractTongYiServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(TongYiService.class);
    private final SpeechClient speechClient;
    @Autowired
    public TongYiAudioSimpleServiceImpl(SpeechClient client) {
        this.speechClient = client;
    }
    @Override
    public String genAudio(String text) {
        logger.info("gen audio prompt is: {}", text);
        SpeechPrompt prompt = new SpeechPrompt(text);
        var resWAV = speechClient.call(prompt).getResult().getOutput();
        // save的代码省略，就是将音频保存到本地而已
        return save(resWAV, SpeechSynthesisAudioFormat.WAV.getValue());
    }

    private String save(ByteBuffer resWAV, String value) {
        File file = new File(System.getProperty("user.dir") , "example."+value); // 指定文件路径和名称
        try (FileOutputStream fos = new FileOutputStream(file)) { // 创建FileOutputStream
            fos.getChannel().write(resWAV); // 将ByteBuffer中的数据写入文件
            fos.flush(); // 确保所有数据都被写入文件
        } catch (IOException e) { // 处理可能出现的异常
            e.printStackTrace(); // 打印异常信息
        }
        return "";
    }
}