package com.example.mybatis.controller;

import com.example.mybatis.entity.LoginEnum;

/**
 * 返回处理类.
 */
public class LoginResultUtils{
    /*
    *   当处理成功时返回
    *   @param data
    *   @return
    * */

    public static ResultBody getSuccessResult(String data) {
        return new ResultBody(LoginEnum.SUCCESS.getCode(), LoginEnum.SUCCESS.getMsg(), data);
    }

    /*
    *   错误返回
    *   @param data
    *   @return
    * */

    public static ResultBody getErrorResult(LoginEnum loginEnum) {
        if(loginEnum == null) {
            loginEnum = LoginEnum.NOTALLOWNULL;
        }
        return new ResultBody(LoginEnum.SUCCESS.getCode(), LoginEnum.SUCCESS.getMsg());

    }

    /*
    *   自定义错误返回结果
    *   @param state
    *   @param msg
    *   @param data
    *   @return
    * */
    public static ResultBody getErrorResult(String state, String msg, String data) {
        return new ResultBody(state, msg, data);
    }


}
