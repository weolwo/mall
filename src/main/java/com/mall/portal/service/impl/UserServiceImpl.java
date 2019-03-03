package com.mall.portal.service.impl;

import com.mall.common.ConstantCode;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.common.TokenCache;
import com.mall.dao.UserMapper;
import com.mall.pojo.User;
import com.mall.portal.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * 用户模块业务层实现
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public ServerResponse<User> login(String username, String password) {
        int resultValue = userMapper.checkUser(username);
        if (resultValue == 0) {
            return ServerResponse.createByErrorMessage(ResponseCode.NOT_USERNAME.getMessage());
        }
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        User user = userMapper.login(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.NOT_PASSWORD.getMessage());
        }
        //把密码置空
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(ResponseCode.LOGIN_SUCCESS.getMessage(), user);
    }

    public ServerResponse register(User user) {
        ServerResponse<String> response = checkValid(user.getUsername(), ConstantCode.USERNAME);
        if (!response.isSuccess()) {
            return response;
        }
        //检查邮箱
        response = checkValid(user.getEmail(), ConstantCode.EMAIL);
        if (!response.isSuccess()) {
            return response;
        }
        //设置用户默认的角色
        user.setRole(ConstantCode.Role.ROLE_COMMON);
        //加密密码
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        int resultValue = userMapper.insert(user);
        if (resultValue == 0) {
            return ServerResponse.createByErrorMessage(ResponseCode.REGISTER_FAIL.getMessage());
        }
        return ServerResponse.createBySuccessMessage(ResponseCode.REGISTER_SUCCESS.getMessage());
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            if (ConstantCode.USERNAME.equals(type)) {
                int resultValue = userMapper.checkUser(str);
                if (resultValue > 0) {
                    return ServerResponse.createByErrorMessage(ResponseCode.USERNAME_EXIST.getMessage());
                }
            } else {
                int resultValue = userMapper.checkEmail(str);
                if (resultValue > 0) {
                    return ServerResponse.createByErrorMessage(ResponseCode.EMAIL_EXIST.getMessage());
                }
            }
        } else {
            return ServerResponse.createByErrorMessage(ResponseCode.PARAMERROR.getMessage());
        }
        return ServerResponse.createBySuccessMessage(ResponseCode.VALIDSUCCESS.getMessage());
    }

    @Override
    public ServerResponse<String> forgetGetQuestion(String username) {
        //先校验用户是否存在
        ServerResponse<String> response = checkValid(username, ConstantCode.USERNAME);
        if (response.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String question = userMapper.forgetGetQuestion(username);
        if (StringUtils.isBlank(question)) {
            return ServerResponse.createByErrorMessage(ResponseCode.QUESTION_NULL.getMessage());
        }
        return ServerResponse.createBySuccess(question);
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount==0) {
            return ServerResponse.createByErrorMessage(ResponseCode.ANWSER_FAIL.getMessage());
        }
        //说明答案是正确的
        String forgetToken = UUID.randomUUID().toString();
        //缓存token
        TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
        //在缓存中存放该用户的token
        return ServerResponse.createBySuccess(forgetToken);
    }

    @Override
    public ServerResponse<String> forgetPassword(String username, String newPassword, String token) {
        //校验用户名是否存在
        int resultValue = userMapper.checkUser(username);
        if (resultValue == 0) {
            return ServerResponse.createByErrorMessage(ResponseCode.NOT_USERNAME.getMessage());
        }
        //校验token
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("无效的token");
        }
        //取出缓存中的token
        String forgetToken = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.equals(forgetToken, token)) {
            //加密密码
            String md5Password =DigestUtils.md5DigestAsHex(newPassword.getBytes());
            int resultCount = userMapper.forgetPassword(username, md5Password);
            if (resultCount > 0) {
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }

        } else {
            return ServerResponse.createByErrorMessage("token错误,请重新获取token");
        }
        return ServerResponse.createByErrorMessage("密码修改失败");
    }

    @Override
    public ServerResponse<String> resetPassword(String oldPassword, String newPassword,User user) {
        //校验输入的老密码是否正确
        int resultCount = userMapper.checkPassword(DigestUtils.md5DigestAsHex(oldPassword.getBytes()), user.getId());
        if(resultCount>0){
            user.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
            resultCount= userMapper.updateByPrimaryKeySelective(user);
            if (resultCount>0){
                return ServerResponse.createBySuccessMessage("密码修改成功");
            }else {
                return ServerResponse.createByErrorMessage("密码修改失败,请重试");
            }
        }
        return ServerResponse.createByErrorMessage("输入的密码不正确,请重试");
    }

    @Override
    public ServerResponse<User> updateUserInfo(User user) {
        //首先应该校验用户更新的邮箱不是当前用户之前的
        int resultCount = userMapper.checkByUserIdEmail(user.getId(), user.getEmail());
        if(resultCount>0){
            return ServerResponse.createByErrorMessage("请更换新的邮箱");
        }
        resultCount = userMapper.updateByPrimaryKeySelective(user);
        if (resultCount>0){
            return ServerResponse.createBySuccess("更新成功",user);
        }
        return ServerResponse.createByErrorMessage("信息更新失败");
    }

    @Override
    public ServerResponse<User> getUserInfomation(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user==null){
          return  ServerResponse.createByErrorMessage("用户不存在");
        }
        return ServerResponse.createBySuccess(user);
    }
}
