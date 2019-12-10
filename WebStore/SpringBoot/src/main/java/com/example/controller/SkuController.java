package com.example.controller;

import com.example.entity.Sku;
import com.example.mapper.SkuMapper;
import com.example.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SkuController {
    private String msg;

    @Autowired
    SkuMapper skuMapper;

    //根据商品id查询库存
    @RequestMapping("/api/sku/getone")
    public JsonResult<Sku> findById(@RequestParam(name = "product_id") int product_id) {
        List<Sku> sku = skuMapper.selectSku(product_id);
        if(sku!=null){
            msg="查询成功";
            Map map=new HashMap<String,String>();
            map.put("data",sku);

            return new JsonResult(map,true);
        }else {
            msg="查询失败";
            return new JsonResult(msg,false);
        }

    }

    //根据id删除库存
    @RequestMapping(value = "/api/delsku/{id}", method = {RequestMethod.POST})
    public JsonResult deletesku(@PathVariable(name = "id") String id){
        if(id!=null){
            skuMapper.deleteSku(Long.valueOf(id));
            msg="删除成功";
            return new JsonResult(msg,true);
        }else {
            msg="删除失败";
            return new JsonResult(msg,false);
        }
    }

    //新增库存数据
    @PostMapping(path = "/api/add/sku")
    public JsonResult<Sku> addproduct(Sku sku){
        System.out.println(sku);
        if(sku!=null){
            skuMapper.insertSku(sku);
            msg="添加成功";
            return new JsonResult(msg,true);
        }
        msg="服务器繁忙";
        return new JsonResult(msg,false);
    }
    //修改库存数据
    @PostMapping(path="/api/updatesku")
    public  JsonResult<Sku> updatesku(Sku sku){
        if(sku!=null){
            skuMapper.updateSku(sku);
            msg="修改成功";
            return  new JsonResult(msg,true);
        }else {
            msg="修改失败";
            return new JsonResult(msg,false);
        }
    }
}
