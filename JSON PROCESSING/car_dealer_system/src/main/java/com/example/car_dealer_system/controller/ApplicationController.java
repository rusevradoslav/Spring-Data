package com.example.car_dealer_system.controller;

import com.example.car_dealer_system.domain.dtos.Query2.CarsDto;
import com.example.car_dealer_system.domain.dtos.Query1.CustomerDto;
import com.example.car_dealer_system.domain.dtos.Query3.LocalSuppliersDto;
import com.example.car_dealer_system.domain.dtos.Query4.CarWithPartsDto;
import com.example.car_dealer_system.domain.dtos.Query5.CustomerWithCars;
import com.example.car_dealer_system.domain.dtos.seedData.CarSeedDto;
import com.example.car_dealer_system.domain.dtos.seedData.CustomerSeedDto;
import com.example.car_dealer_system.domain.dtos.seedData.PartSeedDto;
import com.example.car_dealer_system.domain.dtos.seedData.SupplierSeedDto;
import com.example.car_dealer_system.services.*;
import com.example.car_dealer_system.utils.FileUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.example.car_dealer_system.constants.GlobalConstants.*;

@Controller
public class ApplicationController implements CommandLineRunner {

    private final BufferedReader bufferedReader;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final CarService carService;
    private final CustomerService customerService;
    private final PartService partService;
    private final SaleService saleService;
    private final SupplierService supplierService;

    public ApplicationController(ModelMapper modelMapper, BufferedReader bufferedReader, Gson gson, FileUtil fileUtil, CarService carService, CustomerService customerService, PartService partService, SaleService saleService, SupplierService supplierService) {
        this.bufferedReader = bufferedReader;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.carService = carService;
        this.customerService = customerService;
        this.partService = partService;
        this.saleService = saleService;
        this.supplierService = supplierService;
    }

    @Override
    public void run(String... args) throws Exception {

        startProgram();
        String taskNumber = bufferedReader.readLine();
        while (true) {
            switch (taskNumber) {
                case "0":
                    seedSupplier();
                    seedPart();
                    seedCar();
                    seedCustomer();
                    seedSale();
                    break;
                case "1":
                    orderedCustomersByBirthDate();
                    break;
                case "2":
                    carsFromToyota();
                    break;
                case "3":
                    localSuppliers();
                    break;
                case "4":
                    carsWithTheirListOfParts();
                    break;
                case "5":
                    CustomerWithCars();
                    break;
                default:
                    System.out.println("WARNING: Wrong taskNumber:");
            }


            System.out.println("If you wanna EXIT type (end) else type number for different query:");

            taskNumber = bufferedReader.readLine();
            if (taskNumber.equals("end")) {
                System.out.println("Thank you and have a nice day");
                return;
            }
        }


    }

    private void CustomerWithCars() {
        List<CustomerWithCars> customerWithCars = this.customerService.getCustomersWithCars();
    }

    private void carsWithTheirListOfParts() throws IOException {
        List<CarWithPartsDto> carWithPartsDtos = this.carService.getAllCarsWithParts();
        String json = this.gson.toJson(carWithPartsDtos);
        this.fileUtil.write(json,FOURTH_QUERY);
    }

    private void localSuppliers() throws IOException {
      List<LocalSuppliersDto> localSuppliersDtos = this.supplierService.getAllSuppliersWithPartsCount();
      String json = this.gson.toJson(localSuppliersDtos);
      this.fileUtil.write(json,THIRD_QUERY);

    }

    private void carsFromToyota() throws IOException {
        List<CarsDto>  carsDtos = this.carService.getAllCarsMadeByToyota();
        System.out.println();
        String json = this.gson.toJson(carsDtos);
        System.out.println();
        this.fileUtil.write(json,SECOND_QUERY);
        System.out.println();

    }

    private void orderedCustomersByBirthDate() throws IOException {
        List<CustomerDto> customerDtos = this.customerService.getAllByBirthDate();
        String json = this.gson.toJson(customerDtos);
        this.fileUtil.write(json,FIRST_QUERY);
    }

    private void seedSale() {
        this.saleService.seedSales();
    }

    private void seedCustomer() throws FileNotFoundException {
        CustomerSeedDto[] customerSeedDtos = this.gson.fromJson(new FileReader(CUSTOMERS_FILE_PATH), CustomerSeedDto[].class);
        System.out.println();
        this.customerService.seedCustomers(customerSeedDtos);
    }

    private void seedCar() throws FileNotFoundException {
        CarSeedDto[] carSeedDtos = this.gson.fromJson(new FileReader(CARS_FILE_PATH), CarSeedDto[].class);
        this.carService.seedCars(carSeedDtos);
    }

    private void seedPart() throws FileNotFoundException {
        PartSeedDto[] partSeedDtos = this.gson.fromJson(new FileReader(PARTS_FILE_PATH), PartSeedDto[].class);
        this.partService.seedParts(partSeedDtos);
    }

    private void seedSupplier() throws FileNotFoundException {
        SupplierSeedDto[] supplierSeedDtos = this.gson.fromJson(new FileReader(SUPPLIERS_FILE_PATH), SupplierSeedDto[].class);
        this.supplierService.seedSuppliers(supplierSeedDtos);
    }

    private void startProgram() {
        System.out.printf("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t%s%n", "<<< WELLCOME >>>");
        System.out.printf("%n\t\t\t\t\t\t\t\t\t\t\t\t\t%s%n%n", INFO);
        System.out.println("To seed data into base first press 0 else press query number:");
    }
}
