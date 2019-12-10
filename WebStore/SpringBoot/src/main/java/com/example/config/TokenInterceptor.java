package com.example.config;

import com.example.entity.Token;
import com.example.mapper.TokenMapper;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TokenMapper tokenMapper;

    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Methods", "*");
        String requestURI=request.getRequestURI();
        System.out.println("拦截器"+requestURI);
        String token=request.getParameter("token");
        if(token==null){
            response.setHeader("Content-Type","application/json");
            response.getWriter().write("{\"status\":false,\"data\":\"INVALID_TOKEN\",\"code\":\"INVALID_TOKEN\"}");
            return false;
        }
        Token sourceToken=tokenMapper.relatedToekn(token);
        if(sourceToken==null){
            response.setHeader("Content-Type","application/json");

            response.getWriter().write("{\"status\":false,\"data\":\"INVALID_TOKEN\",\"code\":\"INVALID_TOKEN\"}");
            return false;
        }
        return true;
    }

}
