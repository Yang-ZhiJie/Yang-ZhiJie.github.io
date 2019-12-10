package com.example.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class ProductTag {
    //标签id
    private long id;
    //商品id
//    @NotEmpty(message = "商品ID不允许为空")
//    @Length(min = 2, max = 10, message = "姓名 长度必须在 {min} - {max} 之间")
    private  int product_id;
    //标签类型id
//    @NotEmpty(message = "标签类型不允许为空")
//    @Min(value = 1)
    @Max(value = 3)
    private int tag_id;
    //标签值
    @NotEmpty(message = "标签值不允许为空")
    private String value;
    //状态
    private String status;
    // 创建时间
    private String created_at;
    //修改时间
    private String updated_at;

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

    public int getTagId() {
        return tag_id;
    }

    public void setTagId(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        return "ProductTag{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", tag_id=" + tag_id +
                ", value='" + value + '\'' +
                ", status='" + status + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
