package com.example.car_dealer_system.services.impl;

import com.example.car_dealer_system.domain.dtos.Query2.CarsDto;
import com.example.car_dealer_system.domain.dtos.Query4.CarDto;
import com.example.car_dealer_system.domain.dtos.Query4.CarWithPartsDto;
import com.example.car_dealer_system.domain.dtos.Query4.PartsDto;
import com.example.car_dealer_system.domain.dtos.seedData.CarSeedDto;
import com.example.car_dealer_system.domain.entities.Car;
import com.example.car_dealer_system.repositories.CarRepository;
import com.example.car_dealer_system.services.CarService;
import com.example.car_dealer_system.services.PartService;
import com.example.car_dealer_system.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
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
    public void seedCars(CarSeedDto[] carSeedDtos) {
        Arrays.stream(carSeedDtos).forEach(carSeedDto -> {
            if (validationUtil.isValid(carSeedDto)) {
                if (checkIfExist(carSeedDto)) {
                    System.out.printf("%s %s with that name already exist!", carSeedDto.getMake(), carSeedDto.getModel());
                    return;
                }
                Car car = this.modelMapper.map(carSeedDto, Car.class);
                car.setParts(new HashSet<>(this.partService.getBetween10And20RandomParts()));
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
    public List<CarsDto> getAllCarsMadeByToyota() {
        List<Car> carsMadeByToyota = this.carRepository.findAllCarsMadeByToyota("Toyota");

        List<CarsDto> carsDtos = carsMadeByToyota.stream().map(car -> {

            CarsDto carToyota = this.modelMapper.map(car, CarsDto.class);
            return carToyota;

        }).collect(Collectors.toList());
        System.out.println();
        return carsDtos;
    }

    @Override
    public List<CarWithPartsDto> getAllCarsWithParts() {
        List<Car> cars = this.carRepository.findAll();

        List<CarWithPartsDto> carWithPartsDtos = new ArrayList<>();

        cars.stream().forEach(car -> {
            CarWithPartsDto cpDto = new CarWithPartsDto();

            CarDto carDto = this.modelMapper.map(car, CarDto.class);
            List<PartsDto> partsDtos = new ArrayList<>();

            car.getParts().stream().forEach(part -> {
                        PartsDto pDto = this.modelMapper.map(part, PartsDto.class);
                        partsDtos.add(pDto);
                    }
            );

            cpDto.setCar(carDto);
            cpDto.setParts(partsDtos);
            carWithPartsDtos.add(cpDto);

        });
        return carWithPartsDtos;
    }

    private boolean checkIfExist(CarSeedDto carSeedDto) {
        Car car = this.carRepository
                .findFirstByMakeAndModelAndTravelledDistance(carSeedDto.getMake()
                        , carSeedDto.getModel()
                        , carSeedDto.getTravelledDistance());

        return car != null;
    }
}
