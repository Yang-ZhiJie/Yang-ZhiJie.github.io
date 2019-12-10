package com.example.controller;

import com.example.entity.Product;
import com.example.entity.ProductType;
import com.example.mapper.TypeMapper;
import com.example.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TypeController {
    private String msg;
    @Autowired
    TypeMapper typeMapper;

    //获取所有类别
    @GetMapping("api/type/getall")
    public JsonResult findAllType(){
        List<ProductType> productType=typeMapper.getAll();
//        System.out.println(products);
        if(productType!=null){
            Map map=new HashMap<String,String>();
            map.put("data",productType);

            return new JsonResult(map,true);
        }
        msg="暂无数据";
        return new JsonResult(msg,false);
    }

    //根据类别id查询类别
    @RequestMapping("/api/get/product")
    public ProductType findById(@RequestParam(name = "id") long id){
        ProductType productType=typeMapper.getOne(id);
        return productType;
    }

    //添加类别
    @PostMapping(path = "/api/addtype")
    public JsonResult<ProductType> addproduct(ProductType productType){
        System.out.println(productType);
        if(productType!=null){
            typeMapper.insert(productType);
            msg="添加成功";
            return new JsonResult(msg,true);
        }
        msg="添加失败";
        return new JsonResult(msg,false);
    }

    //删除类别
    @RequestMapping(value = "/api/deletetype/{id}", method = {RequestMethod.POST})
    public JsonResult deleteproduct(@PathVariable(name = "id") String id){
        if(id!=null){
            typeMapper.delete(Long.valueOf(id));
            msg="修改成功";
            return new JsonResult(msg,true);
        }
        msg="修改失败";
        return new JsonResult(msg,false);
    }

    //修改类别信息
    @RequestMapping("/api/update/type")
    public JsonResult<ProductType> update(ProductType productType) {

        typeMapper.update(productType);

        return new JsonResult<ProductType>(productType);
    }
}
