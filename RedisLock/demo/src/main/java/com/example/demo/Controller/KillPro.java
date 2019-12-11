package com.example.demo.Controller;

import com.example.demo.Mapper.OrderMapper;
import com.example.demo.Util.JsonResult;
import com.example.demo.entity.KillProduct;
import com.example.demo.entity.Order;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class KillPro  implements CommandLineRunner {
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    RedisTemplate<Object, Object> redisTemplates;
    @Autowired
    OrderMapper orderMapper;


    //项目启动，查询数据库，将秒杀商品的信息和库存存入redis缓存中
//    @Component
    @Override
    public void run(String... args) throws Exception {
        List<KillProduct> productsList = orderMapper.selectKillProducts();
//        System.out.println(productsList);
        if (productsList != null) {
            RedisSerializer redisSerializer = new StringRedisSerializer();
            redisTemplates.setKeySerializer(redisSerializer);
            redisTemplates.setHashKeySerializer(redisSerializer);
            redisTemplates.setStringSerializer(redisSerializer);
            redisTemplates.setValueSerializer(redisSerializer);
            redisTemplates.setHashValueSerializer(redisSerializer);
            for (int i = 0; i < productsList.size(); i++) {
                redisTemplates.opsForHash().put("killList", productsList.get(i).getId(), productsList.get(i).getQuantity());
            }
//            System.out.println("hahahha");
        }
    }

    @RequestMapping("/api/killpro")
    public JsonResult creatOrder( @RequestParam(name = "userId") String userId, @RequestParam(name = "productId")String productId) {
        System.out.println(productId);
        Object proSku =  redisTemplates.opsForHash().get("killList",productId);
        System.out.println(proSku);
//        int skuPro=Integer.valueOf((Integer) proSku);
        int skuPro=Integer.parseInt((String)proSku);
        //判断redis中秒杀商品余量，直接拦截
        if (skuPro < 0) {
            return new JsonResult("秒杀商品已售馨", false);
        }
        //判断用户是否参与过秒杀
        long sf = redisTemplates.opsForSet().add("userList", userId);
        if (sf == 0) {
            return new JsonResult("已成功参与商品A的秒杀，请勿反复操作", false);
        } else {
            long hashIncLong=redisTemplates.opsForHash().increment("killList",productId,-1);
            System.out.println("减库存操作"+hashIncLong);
            if(hashIncLong<0){
                return new JsonResult(false);
            }
            //生成订单 存入redis中的list
//                String keyProduct=Integer.toString(productId);
            redisTemplates.opsForList().rightPush(productId, userId);
            List<Object> list = redisTemplates.opsForList().range(productId, 0, -1);
            if (list != null) {
                String user = (String) redisTemplates.opsForList().rightPop(productId);
                System.out.println("推出的用户" + user);
                if (user != null) {
                    insertToMysql(userId, productId);
//                    return new JsonResult(true);
                } else {
                    return new JsonResult(false);
                }
            }
            return new JsonResult(false);
        }
    }

        //一有订单生成 直接调用数据库
//    @Transactional
    @Scheduled(fixedDelay = 1000)
        public void insertToMysql (String userId,String product){
            while (true) {
//            long orderSize=redisTemplates.opsForHash().size("orderList");
                if (userId != null) {
                    if (Integer.valueOf(product) == 1) {
                        //生成订单插数据库
                        Order order = new Order();
                        order.setUserOwner(userId);
                        order.setOrderProduct(product);
                        order.setOrderId(UUID.randomUUID().toString());
                        if (order != null) {
                            System.out.println(order);
                            orderMapper.insertOrder(order);
                            orderMapper.updateSku(1);
                        } else if (Integer.valueOf(product) == 2) {
                            //生成订单插数据库
                            Order order1 = new Order();
                            order1.setUserOwner(userId);
                            order1.setOrderProduct("2");
                            order1.setOrderId(UUID.randomUUID().toString());
                            if (order1 != null) {
                                orderMapper.insertOrder(order);
                                orderMapper.updateSku(2);
                            }
                        }
                    }
                    break;
                }
            }
        }

}
