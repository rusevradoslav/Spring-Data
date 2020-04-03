package com.example.car_product.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    private String make;
    private String model;
    private Long travelledDistance;
    private Set<Part> parts;

    public Car() {
    }

    @Column
    public String getMake() {
        return make;
    }

    @Column
    public String getModel() {
        return model;
    }

    @Column
    public Long getTravelledDistance() {
        return travelledDistance;
    }

    @ManyToMany
    public Set<Part> getParts() {
        return parts;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }
}
