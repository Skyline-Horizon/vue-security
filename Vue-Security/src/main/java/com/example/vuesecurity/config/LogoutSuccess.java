package com.example.vuesecurity.config;

import com.alibaba.fastjson.JSON;
import com.example.vuesecurity.entity.Result;
import com.example.vuesecurity.entity.ResultException;
import com.example.vuesecurity.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author 张乔
 * @Date 2024/4/1 13:58
 */
@Component
public class LogoutSuccess implements LogoutSuccessHandler {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String token = request.getHeader("token");
        if (token == null){
            throw new ResultException(555,"先去登录");
        }
        Integer id = jwtUtil.getUsernameFromToken(token);
//        删除redis中的用户信息
        redisTemplate.delete(String.valueOf(id));
        Result<String> result=Result.successMessage("退出成功");


        response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(JSON.toJSONString(result));
    }
}
