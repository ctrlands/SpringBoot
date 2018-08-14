package com.example.mybatis.controller;

import com.example.mybatis.entity.LoginEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常统一处理
 */
@ControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBody handle(Exception e) {
        e.printStackTrace();
        if(e instanceof LoginException) {
            LoginException loginException = (LoginException) e;
            return LoginResultUtils.getErrorResult(loginException.getState(), loginException.getMessage(), loginException.getData());
        }
        return  LoginResultUtils.getErrorResult(LoginEnum.NOTALLOWNULL);

    }
}
