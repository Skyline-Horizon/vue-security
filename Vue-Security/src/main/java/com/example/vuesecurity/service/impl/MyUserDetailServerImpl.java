package com.example.vuesecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.vuesecurity.entity.MyTUserDetail;
import com.example.vuesecurity.entity.Users;
import com.example.vuesecurity.mapper.UsersMapper;
import com.example.vuesecurity.service.MyUserDetailServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailServerImpl implements MyUserDetailServer {
    @Autowired
    UsersMapper userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userService.selectOne(new LambdaQueryWrapper<Users>().
                eq(username != null, Users::getUsername, username));
        if (users == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        MyTUserDetail myTUserDetail=new MyTUserDetail();
myTUserDetail.setUsers(users);
        return myTUserDetail;
    }
}
