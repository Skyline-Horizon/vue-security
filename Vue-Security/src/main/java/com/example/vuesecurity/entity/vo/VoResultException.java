package com.example.vuesecurity.entity.vo;



import com.example.vuesecurity.entity.Result;
import com.example.vuesecurity.entity.ResultException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author 张乔
 * @Date 2023/12/22 20:15
 */
@RestControllerAdvice
public class VoResultException {
//    自定义异常处理类
@ExceptionHandler(ResultException.class)
public Result<String> errorResult(ResultException resultException){
return new Result<>(resultException.getCode(),resultException.getMessage(),null);
}

//全局异常处理类
    @ExceptionHandler(Exception.class)
    public Result<String> error(Exception e) {
    e.printStackTrace();

    return new Result<>(500,e.getMessage(),null);
    }



}
