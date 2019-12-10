package com.example.entity;

import java.util.Date;

public class Sku {
    //库存id
    private long id;
    //商品id
    private int product_id;
    //原价
    private long original_price;
    //售价
    private long price;
    //商品属性1
    private String attr1;
    //商品属性2
    private String attr2;
    //商品属性3
    private String attr3;
    //商品库存
    private int quantity;
    //排序
    private int sort;
    //状态
    private String status;
    //创建时间
    private String created_at;
    //修改时间
    private  String updated_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getProductId() {
        return product_id;
    }

    public void setProductId(int product_id) {
        this.product_id = product_id;
    }

    public long getOriginalPrice() {
        return original_price;
    }

    public void setOriginalPrice(long original_price) {
        this.original_price = original_price;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String  toString() {
        return "Sku{" +
                "id=" + id +
                ", product_id='" + product_id + '\'' +
                ", original_price=" + original_price +
                ", price=" + price +
                ", attr1='" + attr1 + '\'' +
                ", attr2='" + attr2 + '\'' +
                ", attr3='" + attr3 + '\'' +
                ", quantity=" + quantity +
                ", sort=" + sort +
                ", status='" + status + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
