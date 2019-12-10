package com.example.mapper;


import com.example.entity.Sku;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SkuMapper {
    //添加商品类别
    @Insert("insert into pre_sku (product_id,original_price,price,attr1,attr2,attr3,quantity,sort,status) " +
            "values (#{productId},#{originalPrice},#{price},#{attr1},#{attr2},#{attr3},#{quantity},10,1)")
    void insertSku(Sku sku);

    //根据id删除库存
    @Delete("update pre_sku set status=0 WHERE id=#{id}")
    void deleteSku(long id);

    //根据商品id删除库存
    @Delete("update pre_sku set status=0 WHERE product_id=#{id}")
    void deleteSkuByproductId(long id);

    //根据商品id(product_id)查询库存
    @Select("select * from pre_sku where product_id=#{productId}")
    List<Sku> selectSku(int product_id);

    //根据库存id修改库存
    @Update("update pre_sku set original_price=#{originalPrice},price=#{price},quantity=#{quantity} where id=#{id}")
    void updateSku(Sku sku);
}
