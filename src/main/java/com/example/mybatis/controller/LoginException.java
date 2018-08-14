package com.example.mybatis.controller;

import com.example.mybatis.entity.LoginEnum;

/**
 * 自定义异常
 */
public class LoginException extends RuntimeException{
    private String state;
    private  String msg;
    private String data;
    public LoginException() {}

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String state, String message) {
        super(message);
        this.setState(state);
    }
    public LoginException(LoginEnum loginEnum) {
        super(loginEnum.getMsg());
        this.state = loginEnum.getCode();
        //this.msg = loginEnum.getMsg();
        //this.data = loginEnum.getMsg();
    }

    public LoginException(String message, String state, String data) {
        super(message);
        this.setState(state);
        this.setData(data);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
