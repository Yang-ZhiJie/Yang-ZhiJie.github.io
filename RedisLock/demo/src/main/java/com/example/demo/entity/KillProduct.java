package com.example.demo.entity;

import java.io.Serializable;

public class KillProduct implements Serializable {
    //商品排序
    private String id;
    //秒杀商品名称
    private String productKill;
    //商品库存
    private String quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductKill() {
        return productKill;
    }

    public void setProductKill(String productKill) {
        this.productKill = productKill;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "KillProduct{" +
                "id=" + id +
                ", productKill='" + productKill + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
