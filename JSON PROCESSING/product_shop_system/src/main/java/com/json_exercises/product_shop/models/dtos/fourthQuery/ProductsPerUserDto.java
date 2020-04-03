package com.json_exercises.product_shop.models.dtos.fourthQuery;

import com.google.gson.annotations.Expose;
import com.json_exercises.product_shop.models.dtos.seedData.ProductSeedDto;

import java.util.List;

public class ProductsPerUserDto {

    @Expose
    private int count;
    @Expose
    private List<ProductSeedDto> products;

    public ProductsPerUserDto() {
    }

    public int getCount() {
        return count;
    }

    public List<ProductSeedDto> getProducts() {
        return products;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setProducts(List<ProductSeedDto> products) {
        this.products = products;
    }
}
