package com.example.vuesecurity.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;

import com.example.vuesecurity.entity.Result;
import com.example.vuesecurity.entity.vo.VoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author 张乔
 * @Date 2023/12/26 14:06
 */
@RestController
@RequestMapping("/getCaptcha")
public class CaptchaController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping
    public Result getCaptcha() {
        System.out.println("----------------------------------");

//    生成验证码，并放入redis
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 50, 4, 2);
        String codeValue = circleCaptcha.getCode();
        String imageBase64 = circleCaptcha.getImageBase64();

        String codeKey = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(codeKey, codeValue, 5, TimeUnit.MINUTES);
//    "data:images/png;base64,"+imageBase64    直接显示
        VoCode voCode = new VoCode(codeKey, "data:images/png;base64," + imageBase64);
        return Result.successData(voCode);


    }


}
