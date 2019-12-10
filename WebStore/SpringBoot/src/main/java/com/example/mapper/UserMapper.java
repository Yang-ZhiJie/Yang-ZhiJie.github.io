package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    //根据id查询用户
    @Select("SELECT * FROM pre_user WHERE id=#{id}")
    User getOne(long id);

    //根据账户名密码查询用户
    @Select("select id from pre_user where username=#{username}")
    String getUserByName(String username);
}
