package com.xyspace.ai.web.controller;

import com.xyspace.ai.service.tongyi.TongYiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/ai")
@CrossOrigin
public class TongYiController {
    @Autowired
    @Qualifier("tongYiSimpleServiceImpl")
    private TongYiService tongYiSimpleService;

    @Autowired
    @Qualifier("tongYiImagesServiceImpl")
    private TongYiService tongYiImagesService;

    @Autowired
    @Qualifier("tongYiAudioSimpleServiceImpl")
    private TongYiService tongYiAudioService;
    @GetMapping("/simple")
    public String completion(@RequestParam(value = "message", defaultValue = "AI时代下Java开发者该何去何从？") String message) {
        log.info("通义千问 问题 = {}", message);
        return tongYiSimpleService.completion(message);
    }


    @GetMapping("/textToImg")
    public String textToImg(@RequestParam(value = "message", defaultValue = "小孩在夕阳下牵着一条狗") String message) {
        log.info("通义千问 问题 = {}", message);
        return tongYiImagesService.genImg(message).getResult().getOutput().getUrl();
    }

    @GetMapping("/textToSpeech")
    public String textToSpeech(@RequestParam(value = "message", defaultValue = "你好呀！认识你非常高兴，Nice to meet you！") String message) {
        log.info("通义千问 问题 = {}", message);
        return tongYiAudioService.genAudio(message);
    }
}