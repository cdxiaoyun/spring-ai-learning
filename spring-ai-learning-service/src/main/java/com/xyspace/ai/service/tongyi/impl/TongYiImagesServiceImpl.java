package com.xyspace.ai.service.tongyi.impl;

import com.xyspace.ai.service.tongyi.TongYiService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.image.ImageClient;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service("tongYiImagesServiceImpl")
public class TongYiImagesServiceImpl extends AbstractTongYiServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(TongYiService.class);

    @Autowired
    @Qualifier("tongYiImagesClient")
    private ImageClient imageClient;

    @Override
    public ImageResponse genImg(String imgPrompt) {
        var prompt = new ImagePrompt(imgPrompt);
        return imageClient.call(prompt);
    }
}