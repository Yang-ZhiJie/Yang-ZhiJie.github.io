package com.example.controller;

import com.example.entity.Token;
import com.example.mapper.TokenMapper;
import com.example.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @Autowired
    TokenMapper tokenMapper;

    //添加token绑定
//    @RequestMapping("/api/token/set")
//    public JsonResult setToken(@RequestParam(name = "user_id") long user_id){
//        System.out.println(user_id);
//        return new JsonResult();
//        tokenMapper.insert();
//        return token;
//    }
}
