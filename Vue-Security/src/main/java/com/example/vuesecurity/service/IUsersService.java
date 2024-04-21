package com.example.vuesecurity.service;

import com.example.vuesecurity.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vuesecurity.entity.dto.DtoLogin;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 张乔
 * @since 2024-02-26
 */
public interface IUsersService extends IService<Users> {

    String login(DtoLogin dtoLogin);
}
