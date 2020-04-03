package com.example.car_dealer_system.services;

import com.example.car_dealer_system.domain.dtos.Query1.CustomerDto;
import com.example.car_dealer_system.domain.dtos.Query5.CustomerWithCars;
import com.example.car_dealer_system.domain.dtos.seedData.CustomerSeedDto;
import com.example.car_dealer_system.domain.entities.Customer;

import java.util.List;

public interface CustomerService {
    void seedCustomers(CustomerSeedDto[] customerSeedDtos);

    int getAll();

    Customer getRandomCustomer();

    List<CustomerDto> getAllByBirthDate();


    List<CustomerWithCars> getCustomersWithCars();

}
