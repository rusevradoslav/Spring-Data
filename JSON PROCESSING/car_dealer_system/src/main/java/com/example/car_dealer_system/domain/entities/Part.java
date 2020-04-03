package com.example.car_dealer_system.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "parts")
public class Part extends BaseEntity {

    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Supplier supplier;
    private List<Car> cars;

    public Part() {
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    @Column
    public BigDecimal getPrice() {
        return price;
    }

    @Column
    public Integer getQuantity() {
        return quantity;
    }

    @ManyToMany(mappedBy = "parts")
    public List<Car> getCars() {
        return cars;
    }

    @ManyToOne
    public Supplier getSupplier() {
        return supplier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
