package com.example.demo.Mapper;

import com.example.demo.entity.KillProduct;
import com.example.demo.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrderMapper {
    @Insert("Insert into order_product (order_owner,order_id,order_product) values (#{userOwner},#{orderId},#{orderProduct})")
    boolean insertOrder(Order order);

    @Select("select * from order_sku where quantity!=0")
    List<KillProduct> selectKillProducts();

    @Update("update order_sku set quantity = quantity - 1 where id = #{id}")
    void updateSku(int id);
}
