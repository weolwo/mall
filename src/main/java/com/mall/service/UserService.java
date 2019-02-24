package com.mall.service;

import com.mall.common.ServerResponse;

/**
 * 用户模块业务层接口
 */
public interface UserService {

    public ServerResponse login(String username, String password);
}
