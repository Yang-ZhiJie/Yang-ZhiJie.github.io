package com.example.controller;

import com.example.entity.DataSource;
import com.example.entity.Token;
import com.example.entity.User;
import com.example.mapper.TokenMapper;
import com.example.mapper.UserMapper;
import com.example.util.JsonResult;
import com.example.util.TokenSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TokenMapper tokenMapper;

    private String msg;

    //根据类别id查询类别
    @RequestMapping("/api/getuser")
    public User test2(@RequestParam(name = "id") long id){
        User user=userMapper.getOne(id);
        return user;
    }

    //根据账户姓名和密码查询id
    @PostMapping(path = "/api/user/setToken")
    public JsonResult createToken(User user) {
        //获取用户姓名查询id
        String username=user.getUsername();
        String password=user.getPassword();
        //sql根据姓名查询id
        String id=userMapper.getUserByName(username);
        System.out.println(id);

        if(id==null){
            msg="用户名或密码错误";
            return new JsonResult(msg,false);

        }else {
            //调用工具类生成token
            String setToken=TokenSetter.makeToken();
            //转id
            Long userId=Long.valueOf(id);
            //调用api插入绑定id的token到数据库
            boolean tokenStatus=tokenMapper.insert(setToken, Math.toIntExact(userId));
            System.out.println(tokenStatus);
            //登录成功后token存到data
            if(tokenStatus!=false){
                Map map=new HashMap<String,String>();
                map.put("token",setToken);
                List list=new ArrayList();
                list.add(map);
//                DataSource data=new DataSource();
//                data.setData(map);
                return new JsonResult(list,true);
            }
        }
        msg="用户名或密码错误";
        return new JsonResult(msg,false);
    }
}
