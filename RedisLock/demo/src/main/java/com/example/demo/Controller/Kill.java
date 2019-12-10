package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.tools.jconsole.JConsole;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class Kill {
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    RedisTemplate<String,String> redisTemplates;
    private long timeout;

    //秒杀接口
    @RequestMapping("api/kill")
    public boolean killProduct(@RequestParam(name="id") String id){
        System.out.println("进入方法");
        //为方便redis的key值方便查阅在nosql执行之前 先将其转译为字符串
        RedisSerializer redisSerializer=new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        //随机生成通行者
        String passId= UUID.randomUUID().toString();
        //将key名设置为用户ID
        String key=toString().valueOf(id);
        boolean passWay=redisTemplates.opsForValue().setIfAbsent(key,passId);
        System.out.println("查看是否上锁成功"+passWay);
        if(passWay==false){
            //返回false代表key存在，上锁中不能执行操作
            //判断上锁时间是否过期，如果过期 删除该锁
            long timeSet=redisTemplates.getExpire(key);
            System.out.println("过期时间剩余"+timeSet);
            if(timeSet==1){
                redisTemplates.delete("killer");
                return true;
            }
            return false;
        }else {
            //如果返回true代表key未存在,并执行上锁
            timeout=20;
            redisTemplates.expire(key,timeout, TimeUnit.SECONDS);
        }
        //获取设置的有效时间，如果超时解锁放行
//        redisTemplate.opsForValue().get("killer");
//        if(id!=0&&id<=100){
            for(int i=0;i<5;i++){
                String num= String.valueOf(10-i);
                redisTemplates.opsForValue().increment("killer",-1);
                return true;
            }
//        }
        return false;
    }

}
