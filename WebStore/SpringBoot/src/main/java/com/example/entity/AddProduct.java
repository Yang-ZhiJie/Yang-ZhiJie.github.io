package com.example.entity;

import java.util.List;

public class AddProduct {
    private Product product;
    private List<Sku> sku;
    private ProductTag productTag;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Sku> getSku() {
        return sku;
    }

    public void setSku(List<Sku> sku) {
        this.sku = sku;
    }

    public ProductTag getProductTag() {
        return productTag;
    }

    public void setProductTag(ProductTag productTag) {
        this.productTag = productTag;
    }

    @Override
    public String toString() {
        return "AddProduct{" +
                "product=" + product +
                ", sku=" + sku +
                ", productTag=" + productTag +
                '}';
    }
}
