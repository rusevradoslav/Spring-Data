package com.example.car_product.services;

import com.example.car_product.domain.dtos.Query1.CustomerViewRootDto;
import com.example.car_product.domain.dtos.Query5.CustomersWithCarsRootDto;
import com.example.car_product.domain.dtos.seedData.CustomerSeedDto;
import com.example.car_product.domain.entities.Customer;

import java.util.List;

public interface CustomerService {
    void seedCustomers(List<CustomerSeedDto> customerSeedDtos);

    int getAll();

    Customer getRandomCustomer();

    CustomerViewRootDto getAllOrderedCutomers();

    CustomerViewRootDto getAllByBirthDate();

    CustomersWithCarsRootDto getCustomersWithCars();

}
