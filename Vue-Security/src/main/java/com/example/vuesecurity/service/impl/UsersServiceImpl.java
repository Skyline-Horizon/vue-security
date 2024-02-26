package com.example.vuesecurity.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.vuesecurity.entity.MyTUserDetail;
import com.example.vuesecurity.entity.ResultException;
import com.example.vuesecurity.entity.Users;
import com.example.vuesecurity.entity.dto.DtoLogin;
import com.example.vuesecurity.mapper.UsersMapper;
import com.example.vuesecurity.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vuesecurity.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张乔
 * @since 2024-02-26
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public String login(DtoLogin dtoLogin) {
        String codeRedis = redisTemplate.opsForValue().get(dtoLogin.getCodeKey());
        if (!dtoLogin.getCodeValue().equals(codeRedis)){
            throw new ResultException(400,"验证码错误");
        }
        // 验证码正确，删除redis中的验证码
        redisTemplate.delete(dtoLogin.getCodeKey());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dtoLogin.getUsername(),dtoLogin.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(authenticate==null){
            throw new  ResultException(400,"用户名或密码错误");
        }
//        获取返回的用户信息
        Object principal = authenticate.getPrincipal();

        MyTUserDetail myTUserDetail=(MyTUserDetail) principal;
        System.out.println(myTUserDetail);
//        使用Jwt生成token，并将用户的id传入
        String token = jwtUtil.generateToken(myTUserDetail.getUsers().getId());
        redisTemplate.opsForValue().
                set(String.valueOf(myTUserDetail.getUsers().getId()), JSON.toJSONString(myTUserDetail),1, TimeUnit.DAYS);


        return token;
    }

}
