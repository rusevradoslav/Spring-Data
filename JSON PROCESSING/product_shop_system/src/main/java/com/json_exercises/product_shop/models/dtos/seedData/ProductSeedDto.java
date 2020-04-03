package com.json_exercises.product_shop.models.dtos.seedData;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ProductSeedDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    public ProductSeedDto() {
    }

    @Column(nullable = false)
    @Length(min = 3, message = "Name must be at least 3 symbols!")
    public String getName() {
        return name;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
