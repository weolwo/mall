package com.mall.common;

/**
 * 常量类
 */
public class ConstantCode {
    public static final String CURRENT_USER="current_user";
    public static final String USERNAME="username";
    public static final String EMAIL="email";

    //常量分组
    public interface Role{
        int ROLE_COMMON=0;//普通用户
        int ROLE_ADMIN=1;//管理员
    }
}
