package com.example.car_dealer_system.services;

import com.example.car_dealer_system.domain.dtos.Query2.CarsDto;
import com.example.car_dealer_system.domain.dtos.Query4.CarWithPartsDto;
import com.example.car_dealer_system.domain.dtos.seedData.CarSeedDto;
import com.example.car_dealer_system.domain.entities.Car;

import java.util.List;

public interface CarService {
    void seedCars(CarSeedDto[] carSeedDtos);

    Car getRandomCar();

    List<CarsDto> getAllCarsMadeByToyota();

    List<CarWithPartsDto> getAllCarsWithParts();

}
