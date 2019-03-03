package com.mall.portal.service;

import com.mall.common.ServerResponse;
import com.mall.pojo.User;

/**
 * 用户模块业务层接口
 */
public interface UserService {

    public ServerResponse<User> login(String username, String password);

    ServerResponse<String>  register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse<String> forgetGetQuestion(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse<String> forgetPassword(String username, String newPassword, String token);

    ServerResponse<String> resetPassword(String oldPassword, String newPassword, User user);

    ServerResponse<User> updateUserInfo(User user);

    ServerResponse<User> getUserInfomation(Integer id);
}
