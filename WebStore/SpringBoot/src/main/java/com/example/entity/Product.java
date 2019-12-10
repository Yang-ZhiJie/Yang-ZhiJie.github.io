package com.example.entity;

import java.util.Date;

public class Product {
    //商品id
    private long id;
    //商品分类id
    private int category_id;
    //商品名称
    private String name;
    //商品销量
    private long sale_num;
    //商品描述
    private String content;
    //商品排序
    private int sort;
    //商品状态
    private String status;
    //创建时间
    private String created_at;
    //修改时间
    private String updated_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCategoryId() {
        return category_id;
    }

    public void setCategoryId(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSaleNum() {
        return sale_num;
    }

    public void setSaleNum(long sale_num) {
        this.sale_num = sale_num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", category_id=" + category_id +
                ", name='" + name + '\'' +
                ", sale_num=" + sale_num +
                ", content='" + content + '\'' +
                ", sort=" + sort +
                ", status='" + status + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
