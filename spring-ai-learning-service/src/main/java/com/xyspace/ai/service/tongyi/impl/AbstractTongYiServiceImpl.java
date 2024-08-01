package com.xyspace.ai.service.tongyi.impl;

import com.xyspace.ai.service.tongyi.TongYiService;
import org.springframework.ai.image.ImageResponse;

public class AbstractTongYiServiceImpl implements TongYiService {

    @Override
    public String completion(String message) {
        return null;
    }

    @Override
    public ImageResponse genImg(String imgPrompt) {
        return null;
    }

    @Override
    public String genAudio(String text) {
        return null;
    }
}
