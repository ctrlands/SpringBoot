package com.example.mybatis.controller;

import com.example.mybatis.entity.LoginEnum;

/**
 * 前端异常展示结构
 */
public class ResultBody<T> {
    private String code; // 错误码
    private String msg; // 提示信息
    private T data; // 返回数据

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultBody(LoginEnum loginEnum) {
        this.code = loginEnum.getCode();
        this.msg = loginEnum.getMsg();
    }

    public ResultBody(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultBody(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultBody(String code) {
        this.code = code;
    }

    public ResultBody() {

    }

    @Override
    public String toString() {
        return "ResultBody{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
