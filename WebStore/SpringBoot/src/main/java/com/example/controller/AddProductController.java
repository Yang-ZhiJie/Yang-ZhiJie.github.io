package com.example.controller;

import com.example.entity.AddProduct;
import com.example.entity.Product;
import com.example.entity.ProductTag;
import com.example.entity.Sku;
import com.example.mapper.ProductsMapper;
import com.example.mapper.SkuMapper;
import com.example.mapper.TagMapper;
import com.example.mapper.TypeMapper;
import com.example.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddProductController {
    @Autowired
    ProductsMapper productsMapper;
    @Autowired
    SkuMapper skuMapper;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    TypeMapper typeMapper;

    private String msg;

    @Transactional
    @RequestMapping("api/addproduct")
    public JsonResult<AddProduct> addproduct(@RequestBody AddProduct addProduct){
        System.out.println(addProduct);
        System.out.println(addProduct.getProduct());
        if(addProduct!=null){

            //添加商品
          Product p1=addProduct.getProduct();
          productsMapper.insert(p1);
            //获得返回的商品ID
            int proId= (int) p1.getId();

            //循环插入库存
            for(int i=0;i<addProduct.getSku().size();i++){
                addProduct.getSku().get(i).setProductId(proId);
                skuMapper.insertSku(addProduct.getSku().get(i));
            }

            //添加标签
            addProduct.getProductTag().setProductId(proId);
            tagMapper.insertTag(addProduct.getProductTag());
//          tagMapper.insertTag(addProduct.getProductTag());
            msg="添加成功";
          return new JsonResult(msg,true);

        }
        msg="服务器繁忙";
        return new JsonResult(msg,false);
    }
}
