package com.example.mapper;

import com.example.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductsMapper {
    //获取所有商品
    @Select("select * from pre_product where status!=0")
    List<Product> getAll();

    //根据id查询商品
    @Select("SELECT * FROM pre_product WHERE id=#{id}")
    Product getOne(long id);

    //增加商品
    @Insert ("INSERT INTO pre_product " +
            "(category_id,name,sale_num,content,sort,status) " +
            "VALUES(#{categoryId},#{name},0,#{content},1,1)")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insert (Product product);

    //删除商品
    @Delete("update pre_product set status=0 WHERE id =#{id}")
    void delete(long id);

    //修改商品信息
    @Update("UPDATE pre_product SET name=#{name},content=#{content},sale_num=#{saleNum},status=#{status} WHERE id =#{id}")
    void update(Product product);
}
