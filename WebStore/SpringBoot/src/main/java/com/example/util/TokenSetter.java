package com.example.util;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class TokenSetter {
    public TokenSetter(){

    }

    private static  final TokenSetter instance=new TokenSetter();

    private static TokenSetter getInstance(){
        return instance;
    }

    //生成Token
    public static String makeToken(){
        String token=(System.currentTimeMillis()+new Random().nextInt(999999999)+"");
        //数据指纹 128位长 16字节 md5
        try {
            MessageDigest md=MessageDigest.getInstance("md5");
            byte md5[]=md.digest(token.getBytes());
            //base64 编码  任意二进制编码明文字符
            BASE64Encoder encoder=new BASE64Encoder();
            return encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
