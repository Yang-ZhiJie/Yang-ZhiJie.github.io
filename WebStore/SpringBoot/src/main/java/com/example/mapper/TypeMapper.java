package com.example.mapper;

//import com.example.entity.Product;
import com.example.entity.ProductType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TypeMapper {
    //获取所有类别
    @Select("select * from pre_category where status!=0")
    List<ProductType> getAll();

    //根据id查询类别
    @Select("SELECT * FROM pre_category WHERE id=#{id}")
    ProductType getOne(long id);

    //增加类别
    @Insert("insert into pre_category (name,property,sort,status) " +
            "values(#{name},#{property},#{sort},1)")
    void insert (ProductType productType);

    //删除类别
    @Delete("update pre_category set status=0 WHERE id =#{id}")
    void delete(long id);

    //修改类别信息
    @Update("UPDATE pre_category SET name=#{name},sort=#{sort}," +
            "property=#{property} WHERE id =#{id}")
    void update(ProductType productType);
}
