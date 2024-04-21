package com.example.vuesecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 张乔
 * @Date 2023/12/25 20:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultException extends RuntimeException {
    private Integer code;
    private String message;
}
