package com.example.controller;

import com.example.entity.AddNav;
import com.example.mapper.NavMapper;
import com.example.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AddNavController {
    private String msg;
    @Autowired
    NavMapper navMapper;

    //增加导航
    @RequestMapping("api/addnav")
    public JsonResult<AddNav> addnav(AddNav addNav){
        if(addNav!=null){
            System.out.println(addNav);
            navMapper.insertNav(addNav);
            return new JsonResult("添加成功",true);
        }else {
            return new JsonResult("添加失败",false);
        }
    }

    //显示导航页
    @GetMapping("/api/getAllNavs")
    public JsonResult findAll(){
        List<AddNav> navs=navMapper.getAllNav();
//        System.out.println(products);
        if(navs!=null){
            Map map=new HashMap<String,String>();
            map.put("data",navs);

            return new JsonResult(map,true);
        }
        msg="暂无数据";
        return new JsonResult(msg,false);
    }

    //删除导航
    @RequestMapping(value = "/api/delNav/{id}", method = {RequestMethod.POST})
    public JsonResult deleteNav(@PathVariable(name = "id") String id){
        if(id!=null){

            navMapper.deleteNav(Long.valueOf(id));
            msg="删除成功";
            return new JsonResult(msg,true);
        }else {
            msg="删除失败";
            return new JsonResult(msg,false);
        }
    }

    //根据链接目标id查询导航
    @RequestMapping("api/getNavBy")
    public JsonResult<AddNav> getOneByLinkTarget(@RequestParam(name = "linkTarget") long linkTarget){
        List<AddNav> navs = navMapper.getOne(linkTarget);
        if(navs!=null){
            msg="查询成功";
            Map map=new HashMap<String,String>();
            map.put("data",navs);

            return new JsonResult(map,true);
        }else {
            msg="查询失败";
            return new JsonResult(msg,false);
        }
    }

    //修改导航
    @RequestMapping("api/updateNav")
    public JsonResult<AddNav> updateNavById(AddNav addNav){
        if(addNav!=null){

        msg="修改成功";
        navMapper.updateNav(addNav);
        return new JsonResult(msg,true);
        }else {
            msg="修改失败";
            return new JsonResult(msg,false);
        }
    }
}
