package com.mall.dao;

import com.mall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    //用于查询用户名是否存在
    int checkUser(String username);

    int checkEmail(String email);
    //以后登录
    User login(@Param("username") String username,@Param("password") String password);

    String forgetGetQuestion(String username);

    int checkAnswer(@Param("username")String username,@Param("question") String question, @Param("answer")String answer);

    int forgetPassword(@Param("username")String username, @Param("md5Password")String md5Password);

    int checkPassword(@Param("password")String password, @Param("userId")int userId);

    int checkByUserIdEmail(@Param("userId")int userId,@Param("email") String email);
}