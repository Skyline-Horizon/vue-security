package com.example.vuesecurity.entity.dto;

import lombok.Data;

/**
 * @Author 张乔
 * @Date 2024/1/20 21:19
 */
@Data
public class DtoLogin {

    private String username;
    private String password;
    private String codeKey;
    private String codeValue;
    private Boolean rememberMe;


}
