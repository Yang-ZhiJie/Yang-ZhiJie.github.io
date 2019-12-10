package com.example.mapper;

import com.example.entity.Token;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface TokenMapper {
    //根据用户id匹配相对token
    @Select("select token from pre_token where token=#{token}")
    Token relatedToekn(String token);

    //添加token绑定账户记录
    @Insert("insert into pre_token (token,user_id) values (#{token},#{user_id})")
    public boolean insert(String token,int user_id);


}
