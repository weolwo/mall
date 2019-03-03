package com.mall.back;

import com.mall.common.ConstantCode;
import com.mall.common.ServerResponse;
import com.mall.pojo.User;
import com.mall.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 后台用户管理
 */
@RestController
@RequestMapping("/manage/user/")
public class UserBackController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = userService.login(username, password);
        if (response.isSuccess()){
            User user = response.getData();
            //校验权限
            if (user.getRole()==ConstantCode.Role.ROLE_ADMIN){
                //说明是管理员
                session.setAttribute(ConstantCode.CURRENT_USER,response.getData());
            }else {
                return ServerResponse.createByErrorMessage("没有权限");
            }
        }
        //失败直接返回即可
        return response;
    }
}
