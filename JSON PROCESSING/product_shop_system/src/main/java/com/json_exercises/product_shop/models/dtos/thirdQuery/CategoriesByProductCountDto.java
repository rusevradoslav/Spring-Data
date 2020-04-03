package com.json_exercises.product_shop.models.dtos.thirdQuery;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class CategoriesByProductCountDto {

    @Expose
    private String category;
    @Expose
    private int productCount;
    @Expose
    private BigDecimal averagePrice;
    @Expose
    private BigDecimal totalRevenue;

    public CategoriesByProductCountDto() {
    }

    public String getCategory() {
        return category;
    }

    public int getProductCount() {
        return productCount;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
