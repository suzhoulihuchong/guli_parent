package com.atguigu.servicebase.exception;

import com.atguigu.commonutils.Result;
import com.atguigu.servicebase.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("执行了全局异常");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.error().message("执行了特定异常");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(GuliException.class)
    @ResponseBody
    public Result error(GuliException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }
    

    public Result errors(GuliException e){
        log.error("仅仅测试");
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }
    
}
