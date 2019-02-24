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
    int checkUser(@Param("username") String username);
    //以后登录
    int login(@Param("username") String username,@Param("password") String password);
}