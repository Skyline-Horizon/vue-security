package com.example.vuesecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author 张乔
 * @Date 2023/12/22 12:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> successData(T data) {
        return new Result<>(200, "操作成功", data);
    }

    public static <T> Result<T> successMessage(String message) {
        return new Result<>(200, message, null);
    }

    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

}
