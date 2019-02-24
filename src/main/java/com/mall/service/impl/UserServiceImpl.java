package com.mall.service.impl;

import com.mall.common.ServerResponse;
import com.mall.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户模块业务层实现
 */
public class UserServiceImpl {

    @Autowired
    private UserMapper userMapper;

    public ServerResponse login(String username, String password){
        return null;
    }
}
