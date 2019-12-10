package com.example.mapper;

import com.example.entity.ProductTag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TagMapper {
    //添加标签
    @Insert("insert into pre_product_tag (product_id,tag_id,value,status)values(#{productId},#{tagId},#{value},1)")
    boolean insertTag(ProductTag addProduct);

    //根据商品id删除标签
    @Update("update pre_product_tag set status=0 WHERE product_id=#{id}")
    void deleteByProductId(long id);

    //根据id删除标签
    @Update("update pre_product_tag set status=0 WHERE id =#{id}")
    void deleteTagId(long id);

    //根据商品id查询对应标签
    @Select("select * from pre_product_tag where product_id=#{id}")
    List<ProductTag> selectTagByProductId(int product_id);

    //根据id查询对应标签
    @Select("select * from pre_product_tag where id=#{id}")
    ProductTag selectTagById(int id);

    //查询全部标签
    @Select("select * from pre_product_tag where status!=0")
    List<ProductTag> selectAllTag();

    //根据id修改value内容
    @Update("update pre_product_tag  set value=#{value} where id =#{id}")
    void updateTag( ProductTag productTag);
}
