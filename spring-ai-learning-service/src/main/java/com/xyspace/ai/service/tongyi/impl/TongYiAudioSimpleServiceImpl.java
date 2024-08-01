package com.xyspace.ai.service.tongyi.impl;

import com.alibaba.cloud.ai.tongyi.audio.TongYiAudioSpeechOptions;
import com.alibaba.cloud.ai.tongyi.audio.api.SpeechClient;
import com.alibaba.cloud.ai.tongyi.audio.api.SpeechPrompt;
import com.alibaba.dashscope.audio.tts.SpeechSynthesisAudioFormat;
import com.alibaba.dashscope.audio.tts.SpeechSynthesisTextType;
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
@Service("tongYiAudioSimpleServiceImpl")
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
        TongYiAudioSpeechOptions speechOptions = TongYiAudioSpeechOptions.builder().withTextType(SpeechSynthesisTextType.SSML).withFormat(SpeechSynthesisAudioFormat.WAV).withSampleRate(16000).withVolume(50).withRate(1.0f).withPitch(1.0f).withEnableWordTimestamp(false).withEnablePhonemeTimestamp(false).build();

        SpeechPrompt prompt = new SpeechPrompt(text,speechOptions);
        var resWAV = speechClient.call(prompt).getResult().getOutput();
        // save的代码省略，就是将音频保存到本地而已
        return save(resWAV, SpeechSynthesisAudioFormat.WAV.getValue());
    }

    private String save(ByteBuffer resWAV, String value) {
        String fileUrl = System.getProperty("user.dir") + "/example." + value;
        log.info("file url = {}", fileUrl);
        File file = new File(fileUrl); // 指定文件路径和名称
        if (file.exists()) {
            file.delete();
        }
        try (FileOutputStream fos = new FileOutputStream(file)) { // 创建FileOutputStream
            fos.getChannel().write(resWAV); // 将ByteBuffer中的数据写入文件
            fos.flush(); // 确保所有数据都被写入文件
        } catch (IOException e) { // 处理可能出现的异常
            log.error("保存录音文件到本地异常！", e);
        }
        return fileUrl;
    }
}