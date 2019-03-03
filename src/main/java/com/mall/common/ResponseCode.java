package com.mall.common;

/**
 * 统一响应码枚举类
 */
public enum ResponseCode {
    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    NOT_USERNAME(11, "用户名不存在"),
    NOT_PASSWORD(12, "密码错误"),
    LOGIN_SUCCESS(13, "登录成功"),
    LOGOUT_SUCCESS(14, "登出成功"),
    USERNAME_EXIST(15, "用户名已存在"),
    EMAIL_EXIST(16, "邮箱已存在"),
    REGISTER_FAIL(17, "注册失败"),
    REGISTER_SUCCESS(18, "注册成功"),
    PARAMERROR(19, "参数错误"),
    VALIDSUCCESS(20, "校验成功"),
    NOT_LOGIN(20, "未登录"),
    QUESTION_NULL(20, "找回密码的问题为空"),
    ANWSER_FAIL(20, "答案错误"),

    ;

    private Integer code;
    private String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
