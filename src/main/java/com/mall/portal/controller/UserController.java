package com.mall.portal.controller;

import com.mall.common.ConstantCode;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.pojo.User;
import com.mall.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        //调用service方法实现登录
        ServerResponse response = userService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(ConstantCode.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 用户登出接口
     *
     * @return
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    public ServerResponse<String> logout(HttpSession session) {
        //移除用户session
        session.removeAttribute(ConstantCode.CURRENT_USER);
        return ServerResponse.createBySuccessMessage(ResponseCode.LOGOUT_SUCCESS.getMessage());
    }

    /**
     * 注册
     */
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    public ServerResponse<String> register(User user) {
        return userService.register(user);
    }

    /**
     * 校验用户名和邮箱
     */
    @RequestMapping(value = "checkValid.do", method = RequestMethod.POST)
    public ServerResponse<String> checkValid(String str, String type) {
        return userService.checkValid(str, type);
    }

    //获取用户信息
    @RequestMapping(value = "getUserInfo.do", method = RequestMethod.POST)
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(ConstantCode.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.NOT_LOGIN.getMessage());
        }
        return ServerResponse.createBySuccess(user);
    }

    //获取问题
    @RequestMapping(value = "forgetGetQuestion.do", method = RequestMethod.POST)
    public ServerResponse<String> forgetGetQuestion(String username) {
        return userService.forgetGetQuestion(username);
    }
    //检查答案
    @RequestMapping(value = "checkAnswer.do", method = RequestMethod.POST)
    public ServerResponse<String> checkAnswer(String username,String question,String answer){
       return userService.checkAnswer(username,question,answer);
    }
    //忘记密码
    @RequestMapping(value = "forgetPassword.do", method = RequestMethod.POST)
    public ServerResponse<String> forgetPassword(String username,String newPassword,String token){
        return userService.forgetPassword(username,newPassword,token);
    }
    //重置密码
    @RequestMapping(value = "resetPassword.do", method = RequestMethod.POST)
    public ServerResponse<String> resetPassword(String oldPassword,String newPassword,HttpSession session){
        //校验用户是否登录
        User currentUser = (User) session.getAttribute(ConstantCode.CURRENT_USER);
        if (currentUser==null){
           return ServerResponse.createByErrorMessage("请登录后重试");
        }
        return userService.resetPassword(oldPassword,newPassword,currentUser);
    }
    //更新用户信息
    @RequestMapping(value = "updateUserInfo.do", method = RequestMethod.POST)
    public ServerResponse<User> updateUserInfo(User user,HttpSession session){
        //校验用户是否登录
        User currentUser = (User) session.getAttribute(ConstantCode.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorMessage("请登录后重试");
        }
        //取出当前用户的id,用户名和id不能被更新
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = userService.updateUserInfo(user);
        if (response.isSuccess()){
            //更新session中的信息
            session.setAttribute(ConstantCode.CURRENT_USER,response);
        }
        return response;
    }
    //获取用户信息
    @RequestMapping(value = "getUserInfomation.do", method = RequestMethod.POST)
    public ServerResponse<User> getUserInfomation(HttpSession session) {
        //校验用户是否登录,如果没有登录前端需要强制登录
        User currentUser = (User) session.getAttribute(ConstantCode.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMessage());
        }
        ServerResponse<User> userInfomation = userService.getUserInfomation(currentUser.getId());
        return userInfomation;
    }

}
