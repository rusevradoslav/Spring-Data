package com.example.car_dealer_system.domain.dtos.Query4;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CarDto {
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private Long TravelledDistance;

    public CarDto() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return TravelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        TravelledDistance = travelledDistance;
    }
}
