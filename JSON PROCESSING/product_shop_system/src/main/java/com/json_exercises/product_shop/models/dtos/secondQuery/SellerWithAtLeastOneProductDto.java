package com.json_exercises.product_shop.models.dtos.secondQuery;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SellerWithAtLeastOneProductDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private List<ProductWithBuyerDto> soldProducts;

    public SellerWithAtLeastOneProductDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<ProductWithBuyerDto> getSoldProducts() {
        return soldProducts;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSoldProducts(List<ProductWithBuyerDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
