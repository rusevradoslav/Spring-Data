package com.json_exercises.product_shop.models.dtos.fourthQuery;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UsersWithSoldProductsDto {

    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;
    @Expose
    private List<ProductsPerUserDto> soldProducts;

    public UsersWithSoldProductsDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public List<ProductsPerUserDto> getSoldProducts() {
        return soldProducts;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSoldProducts(List<ProductsPerUserDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
