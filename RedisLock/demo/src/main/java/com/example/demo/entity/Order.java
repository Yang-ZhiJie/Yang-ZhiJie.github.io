package com.example.demo.entity;

import java.io.Serializable;

public class Order implements Serializable {
    //排序号
    private long id;
    //用户名
    private String userOwner;
    //生成的随机订单号,订单标号;
    private String orderId;
    //秒杀到商品名称
    private String orderProduct;

    public String getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(String orderProduct) {
        this.orderProduct = orderProduct;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(String userOwner) {
        this.userOwner = userOwner;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userOwner='" + userOwner + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderProduct='" + orderProduct + '\'' +
                '}';
    }
}
