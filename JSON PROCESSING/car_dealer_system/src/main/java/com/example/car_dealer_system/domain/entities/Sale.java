package com.example.car_dealer_system.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {

    private Car car;
    private Customer customer;
    private Double discount;

    public Sale() {
    }

    @ManyToOne
    public Car getCar() {
        return car;
    }

    @ManyToOne
    public Customer getCustomer() {
        return customer;
    }

    @Column
    public Double getDiscount() {
        return discount;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
