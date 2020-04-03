package com.example.car_dealer_system.domain.dtos.Query4;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class PartsDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    public PartsDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
