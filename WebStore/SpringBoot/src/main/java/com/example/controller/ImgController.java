package com.example.controller;

import com.example.util.JsonResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.Map;
import java.util.UUID;

@Configuration

@Controller
public class ImgController implements WebMvcConfigurer {
    private String localUrl="http://localhost:8080/Images";
    private String msg;


    @PostMapping("/api/uploadImg")
    @ResponseBody
//    public JsonResult upFile(@RequestParam("file") MultipartFile file, Map map) {
//        String path = ClassUtils.getDefaultClassLoader().getResource("Images").getPath();
//        String fileName = file.getOriginalFilename();
//        String suffixName=fileName.substring(fileName.lastIndexOf("."));
//        fileName=UUID.randomUUID()+suffixName;
//        String imgPath=File.separator+fileName;
//        String savePath=path+File.separator+imgPath;
//        File saveFile = new File(savePath);
//
//        if (!saveFile.exists()) {
//            saveFile.mkdirs();
//        }
//        try {
//            file.transferTo(saveFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("执行失败");
//            return new JsonResult("上传失败",false);
//        }
//        return new JsonResult(localUrl+imgPath,true);
//
//    }
    public String upFile(@RequestParam("file") MultipartFile file, Map map) {
        //        获取classes路径下“static”的路径
        String path = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
//        System.out.println(file);
        String fileName = file.getOriginalFilename();

        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + suffixName;
        String url_path = "Images" + File.separator + fileName;
        System.out.println(url_path);
        String savePath = path + File.separator + url_path;
        System.out.println("保存路径" + savePath);
        File saveFile = new File(savePath);

        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);
            return url_path;
        } catch (Exception e) {
            ;
            e.printStackTrace();
            msg = "数据异常,请稍后重试";
            return msg;
        }
    }

}
