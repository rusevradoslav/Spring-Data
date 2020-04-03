package com.example.car_product.services;

import com.example.car_product.domain.dtos.Query2.CarMakeFromToyotaRootDto;
import com.example.car_product.domain.dtos.Query4.CarsWithPartsRootDto;
import com.example.car_product.domain.dtos.seedData.CarSeedDto;
import com.example.car_product.domain.entities.Car;

import java.util.List;

public interface CarService {
    void seedCars(List<CarSeedDto> cars);

    Car getRandomCar();

    CarMakeFromToyotaRootDto getAllCarsMakeFromToyota();

    CarsWithPartsRootDto getCarsWithParts();
}
