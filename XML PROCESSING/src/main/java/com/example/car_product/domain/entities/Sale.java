package com.example.car_product.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {
    private Double discount;
    private Customer customer;
    private Car car;

    public Sale() {
    }

    @Column
    public Double getDiscount() {
        return discount;
    }

    @ManyToOne
    public Customer getCustomer() {
        return customer;
    }

    @ManyToOne
    public Car getCar() {
        return car;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
