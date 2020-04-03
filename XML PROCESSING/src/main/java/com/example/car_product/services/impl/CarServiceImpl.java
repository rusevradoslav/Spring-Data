package com.example.car_product.services.impl;

import com.example.car_product.domain.dtos.Query2.CarMakeFromToyotaDto;
import com.example.car_product.domain.dtos.Query2.CarMakeFromToyotaRootDto;
import com.example.car_product.domain.dtos.Query4.CarsWithPartsDto;
import com.example.car_product.domain.dtos.Query4.CarsWithPartsRootDto;
import com.example.car_product.domain.dtos.Query4.PartDto;
import com.example.car_product.domain.dtos.Query4.PartsRootDto;
import com.example.car_product.domain.dtos.seedData.CarSeedDto;
import com.example.car_product.domain.entities.Car;
import com.example.car_product.repositories.CarRepository;
import com.example.car_product.services.CarService;
import com.example.car_product.services.PartService;
import com.example.car_product.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Transactional
@Service
public class CarServiceImpl implements CarService {
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final CarRepository carRepository;
    private final PartService partService;

    public CarServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, CarRepository carRepository, PartService partService) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.carRepository = carRepository;
        this.partService = partService;
    }

    @Override
    public void seedCars(List<CarSeedDto> carSeedDtos) {
        carSeedDtos.stream().forEach(carSeedDto -> {
            if (validationUtil.isValid(carSeedDto)) {
                if (checkIfExist(carSeedDto)) {
                    System.out.printf("%s %s with that name already exist!", carSeedDto.getMake(), carSeedDto.getModel());
                    return;
                }
                Car car = this.modelMapper.map(carSeedDto, Car.class);
                car.setParts(new HashSet<>(this.partService.getBetween10And20RandomParts()));
                System.out.println();
                this.carRepository.saveAndFlush(car);
            } else {
                validationUtil.getViolation(carSeedDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);

            }
        });
    }

    @Override
    public Car getRandomCar() {
        Random random = new Random();
        long randomId = random.nextInt((int) this.carRepository.count()) + 1;
        Car car = this.carRepository.findFirstById(randomId);
        return car;
    }

    @Override
    public CarMakeFromToyotaRootDto getAllCarsMakeFromToyota() {
        CarMakeFromToyotaRootDto carMakeFromToyotaRootDto = new CarMakeFromToyotaRootDto();

        List<CarMakeFromToyotaDto> carMakeFromToyotaDtos =
                this.carRepository.findAllCarsMadeByToyota("Toyota").stream().map(car -> {

                    CarMakeFromToyotaDto carToyota = this.modelMapper.map(car, CarMakeFromToyotaDto.class);


                    return carToyota;

                }).collect(Collectors.toList());
        carMakeFromToyotaRootDto.setCars(carMakeFromToyotaDtos);
        return carMakeFromToyotaRootDto;
    }

    @Override
    public CarsWithPartsRootDto getCarsWithParts() {
        CarsWithPartsRootDto carsWithPartsRootDto = new CarsWithPartsRootDto();

        List<CarsWithPartsDto> carsWithPartsDtos = this.carRepository
                .findAll().stream().map(car -> {
                    CarsWithPartsDto carsWithPartsDto = new CarsWithPartsDto();

                    carsWithPartsDto.setMake(car.getMake());
                    carsWithPartsDto.setModel(car.getModel());
                    carsWithPartsDto.setTraveledDistance(car.getTravelledDistance());


                    PartsRootDto partsRootDto = new PartsRootDto();

                    List<PartDto> partDtos = new ArrayList<>();
                    car.getParts().stream().forEach(part -> {
                        PartDto pDto = this.modelMapper.map(part, PartDto.class);
                        partDtos.add(pDto);
                    });
                    partsRootDto.setParts(partDtos);

                    carsWithPartsDto.setPartsRootDto(partsRootDto);


                    return carsWithPartsDto;
                }).collect(Collectors.toList());
        carsWithPartsRootDto.setCars(carsWithPartsDtos);
        return carsWithPartsRootDto;
    }

    private boolean checkIfExist(CarSeedDto carSeedDto) {
        Car car = this.carRepository
                .findFirstByMakeAndModelAndTravelledDistance(carSeedDto.getMake()
                        , carSeedDto.getModel()
                        , carSeedDto.getTravelledDistance());

        return car != null;
    }
}
