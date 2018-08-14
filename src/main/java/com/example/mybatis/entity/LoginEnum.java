package com.example.mybatis.entity;

/**
 * 登录状态码
 */
public enum LoginEnum {

    SUCCESS("1","登陆成功")
    ,ERRPWD("-1","密码错误")
    ,ERRID("0","账号不存在")
    ,NOTALLOWNULL("-4","不允许为空")
    ;

    private String code;
    private String msg;





    public static LoginEnum getLoginEnum(String code) {
        for (LoginEnum le : LoginEnum.values()) {
            if(le.code.equals(code)) {
                return le;
            }
        }
        return null;
    }

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

    LoginEnum(String code, String msg) {
        this.code = code;
        this.msg =msg;
    }
}
