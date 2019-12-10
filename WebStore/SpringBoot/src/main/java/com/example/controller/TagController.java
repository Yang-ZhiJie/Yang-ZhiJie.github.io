package com.example.controller;

import com.example.entity.Product;
import com.example.entity.ProductTag;
import com.example.mapper.TagMapper;
import com.example.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TagController {
    @Autowired
    TagMapper tagMapper;
    private String msg;

    //获得所有标签
    @GetMapping("api/allTags")
    public JsonResult<ProductTag> getalltags(){
        List<ProductTag> productTags=tagMapper.selectAllTag();
//        System.out.println(products);
        if(productTags!=null){
            Map map = new HashMap<String, String>();
            map.put("data",productTags);

            return new JsonResult(map,true);
        }
        msg="暂无数据";
        return new JsonResult(msg,false);
    }

    @RequestMapping("api/getByProId")
    public JsonResult<ProductTag> findById(@RequestParam(name = "id") int product_id) {
        List<ProductTag> productTags = tagMapper.selectTagByProductId(product_id);
        if(productTags!=null){
            msg="查询成功";
            Map map=new HashMap<String,String>();
            map.put("data",productTags);

            return new JsonResult(map,true);
        }else {
            msg="查询失败";
            return new JsonResult(msg,false);
        }
    }
    @RequestMapping(value = "/api/deltag/{id}", method = {RequestMethod.POST})
    public JsonResult deletetag(@PathVariable(name = "id") String id){
        System.out.println(Long.valueOf(id));
        if(id!=null){

            tagMapper.deleteTagId(Long.valueOf(id));

            msg="删除成功";
            return new JsonResult(msg,true);
        }else {
            msg="删除失败";
            return new JsonResult(msg,false);
        }

    }
    @RequestMapping(value = "/api/updatetag", method = {RequestMethod.POST})
    public JsonResult updatetag(@Valid ProductTag productTag, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new JsonResult<String>(bindingResult.getFieldError().getDefaultMessage(), false);
        }
        ProductTag proTag=tagMapper.selectTagById((int) productTag.getId());

//        System.out.println(Long.valueOf(id));
        if(proTag!=null){
            tagMapper.updateTag(productTag);
            msg="修改成功";
            return new JsonResult(msg,true);
        }else {
            msg="修改失败";
            return new JsonResult(msg,false);
        }

    }
}
