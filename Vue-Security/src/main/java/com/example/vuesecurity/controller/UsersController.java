package com.example.vuesecurity.controller;


import com.alibaba.fastjson.JSON;
import com.example.vuesecurity.entity.MyUserDetail;
import com.example.vuesecurity.entity.Result;
import com.example.vuesecurity.entity.Users;
import com.example.vuesecurity.entity.dto.DtoLogin;
import com.example.vuesecurity.service.IUsersService;
import com.example.vuesecurity.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张乔
 * @since 2024-02-26
 */
@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private IUsersService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Result<String> login(@RequestBody DtoLogin dtoLogin) {
        System.out.println(dtoLogin);
        String token = userService.login(dtoLogin);
        return Result.successData(token);
    }

    @PostMapping("/register")
    public Result register(@RequestBody DtoLogin dtoLogin) {
        System.out.println(dtoLogin);
        Users users = new Users();
        users.setUsername(dtoLogin.getUsername());
        users.setPassword(passwordEncoder.encode(dtoLogin.getPassword()));
        userService.save(users);
        return Result.success();

    }
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/logout")
    public Result logout(@RequestHeader("token")String token){
        Integer id = jwtUtil.getUsernameFromToken(token);
        redisTemplate.delete(String.valueOf(id));
        System.out.println("用户退出=======>");
        return Result.success();
    }

    @GetMapping("/info")
    public Result info(@RequestHeader("token")String token){
        System.out.println("controller层获取到的token=======>"+token);
        Integer id = jwtUtil.getUsernameFromToken(token);
        String redisUser = redisTemplate.opsForValue().get(String.valueOf(id));
        MyUserDetail myTUserDetail = JSON.parseObject(redisUser, MyUserDetail.class);
        return Result.successData(myTUserDetail);

    }

}
