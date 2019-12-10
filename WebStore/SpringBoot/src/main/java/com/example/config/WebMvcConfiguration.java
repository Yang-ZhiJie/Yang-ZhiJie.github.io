package com.example.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@SpringBootApplication
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Resource
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // TODO 这里跨域最好配置域名
                .allowedOrigins("*")
                .maxAge(3600)
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "OPTIONS");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){

        //addInterceptors()的顺序需要严格按照程序的顺序
        InterceptorRegistration registration=registry.addInterceptor(tokenInterceptor);
        registration.addPathPatterns("/**");

        //排除不需要拦截的api
        registration.excludePathPatterns("/error","/api/user/setToken","/Images/*");
    }
}
