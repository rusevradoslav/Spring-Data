package com.example.car_dealer_system.domain.dtos.Query4;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CarWithPartsDto {

    @Expose
    CarDto car;
    @Expose
    List<PartsDto> parts;

    public CarWithPartsDto() {
    }

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }

    public List<PartsDto> getParts() {
        return parts;
    }

    public void setParts(List<PartsDto> parts) {
        this.parts = parts;
    }
}
