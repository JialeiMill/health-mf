package com.shi.healthmf.config;

import com.shi.healthmf.bean.Result;
import com.shi.healthmf.excs.BusinessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 * 全局异常处理
 * @author: Shijialei
 * @date: 2023/8/11
 */
@ResponseBody
@RestControllerAdvice(basePackages = "com.shi.healthmf.controller")
public class GlobalExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public Result<Integer> businessExceptionHandle(BusinessException exception){
        return Result.<Integer>builder().build().fail(exception.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public Result<Integer> exceptionHandle(Exception exception){
        return Result.<Integer>builder().build().fail(exception.getMessage());
    }

}
