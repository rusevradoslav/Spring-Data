package com.example.car_dealer_system.domain.dtos.Query1;

import com.example.car_dealer_system.domain.entities.Car;
import com.example.car_dealer_system.domain.entities.Customer;
import com.google.gson.annotations.Expose;

public class SaleDto {
    @Expose
    private Car car;
    @Expose
    private Customer customer;
    @Expose
    private Double discount;

    public SaleDto() {
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
