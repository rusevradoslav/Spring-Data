package com.example.car_dealer_system.domain.entities;


import javax.persistence.*;
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
    public long getTravelledDistance() {
        return travelledDistance;
    }

    @ManyToMany() //cascade = CascadeType.ALL, fetch = FetchType.EAGER
    public Set<Part> getParts() {
        return parts;
    }



    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setTravelledDistance(long travelDistance) {
        this.travelledDistance = travelDistance;
    }



    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }
}
