package com.example.controller;

import com.example.entity.Product;
import com.example.mapper.NavMapper;
import com.example.mapper.ProductsMapper;
import com.example.mapper.SkuMapper;
import com.example.mapper.TagMapper;
import com.example.util.JsonResult;
import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductsController {
    private String msg;
    @Autowired
    ProductsMapper productsMapper;
    @Autowired
    SkuMapper skuMapper;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    NavMapper navMapper;

    //获取所有商品
    @GetMapping("/api/get/allproducts")
    public JsonResult findAll(){
        List<Product> products=productsMapper.getAll();
//        System.out.println(products);
        if(products!=null){
            Map map=new HashMap<String,String>();
            map.put("data",products);

            return new JsonResult(map,true);
        }
        msg="暂无数据";
    return new JsonResult(msg,false);
    }


    //根据商品id查询商品
    @RequestMapping("/api/getone")
    public Product findById(@RequestParam(name = "id") long id){
        Product products=productsMapper.getOne(id);
        return products;
    }

    //添加商品
    @PostMapping(path = "/api/productadd")
    public JsonResult<Product> addproduct(Product product){
        System.out.println(product);
        if(product!=null){
            productsMapper.insert(product);

//            msg="添加成功";
            return new JsonResult(product,true);
        }
            msg="服务器繁忙";
            return new JsonResult(msg,false);
    }

    //删除商品
    @Transactional
    @RequestMapping(value = "/api/del/{id}", method = {RequestMethod.POST})
    public JsonResult deleteproduct(@PathVariable(name = "id") String id){
        System.out.println(Long.valueOf(id));
        if(id!=null){
            skuMapper.deleteSkuByproductId(Long.valueOf(id));
            tagMapper.deleteByProductId(Long.valueOf(id));
            productsMapper.delete(Long.valueOf(id));
            msg="删除成功";
            return new JsonResult(msg,true);
        }else {
            msg="删除失败";
            return new JsonResult(msg,false);
        }


    }

    //修改商品信息
    @RequestMapping("/api/update")
    public JsonResult<Product> update(Product product) {
        Product pro=productsMapper.getOne(product.getId());
        if(pro!=null){
            productsMapper.update(product);
            msg="修改成功";
            return new JsonResult(msg,true);
        }else{
            msg="修改失败,商品已下架";
            return new JsonResult(msg,false);
        }


    }

}
