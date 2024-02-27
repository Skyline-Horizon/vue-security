package com.example.vuesecurity.controller;

import com.example.vuesecurity.entity.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 张乔
 * @Date 2024/2/27 15:43
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @PreAuthorize("hasAnyAuthority('张乔没有的权限')")
    @GetMapping("/hello")
    public Result hello(){
        System.out.println("test接口中的hello方法调用========================");
        return Result.successData("hello");
    }



}
