package com.json_exercises.product_shop.models.dtos.seedData;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class CategorySeedDto {
    @Expose
    private String name;

    public CategorySeedDto() {
    }

    @NotNull
    @Length(min = 3, max = 15, message = "Name must be between 3 and 15 symbols!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
