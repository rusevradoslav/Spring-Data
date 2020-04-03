package com.example.car_product.controller;

import com.example.car_product.domain.dtos.Query1.CustomerViewRootDto;
import com.example.car_product.domain.dtos.Query2.CarMakeFromToyotaRootDto;
import com.example.car_product.domain.dtos.Query3.LocalSupplierRootDto;
import com.example.car_product.domain.dtos.Query4.CarsWithPartsRootDto;
import com.example.car_product.domain.dtos.Query5.CustomersWithCarsRootDto;
import com.example.car_product.domain.dtos.seedData.CarSeedRootDto;
import com.example.car_product.domain.dtos.seedData.CustomerSeedRootDto;
import com.example.car_product.domain.dtos.seedData.PartSeedRootDto;
import com.example.car_product.domain.dtos.seedData.SupplierSeedRootDto;
import com.example.car_product.services.*;
import com.example.car_product.utils.XmlParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import static com.example.car_product.constants.GlobalConstants.*;
import static com.example.car_product.constants.GlobalConstants.INFO;

@Controller
public class ApplicationController implements CommandLineRunner {
    private final XmlParser xmlParser;
    private final CarService carService;
    private final SupplierService supplierService;
    private final SaleService saleService;
    private final CustomerService customerService;
    private final PartService partService;
    private final BufferedReader bufferedReader;

    public ApplicationController(XmlParser xmlParser, CarService carService, SupplierService supplierService, SaleService saleService, CustomerService customerService, PartService partService, BufferedReader bufferedReader) {
        this.xmlParser = xmlParser;
        this.carService = carService;
        this.supplierService = supplierService;
        this.saleService = saleService;
        this.customerService = customerService;
        this.partService = partService;
        this.bufferedReader = bufferedReader;
    }


    @Override
    public void run(String... args) throws Exception {
        startProgram();
        String numberOfTask = bufferedReader.readLine();

        switch (numberOfTask) {
            case "0":
                seedData();
                break;
            case "1":
                orderedCustomers();
                break;
            case "2":
                carsMakeFromToyota();
                break;
            case "3":
                localSupplier();
                break;
            case "4":
                carsWithParts();
                break;
            case "5":
                customersWithCars();
                break;
            default:
                System.out.println("WARNING: WRONG NUMBER OF TASK");
        }
        System.out.println("If you wanna EXIT type (end) else type number for different query:");

        numberOfTask = bufferedReader.readLine();
        if (numberOfTask.equals("end")) {
            System.out.println("Thank you and have a nice day");
            return;
        }

    }

    private void customersWithCars() throws JAXBException {
        CustomersWithCarsRootDto customersWithCarsRootDto = this.customerService.getCustomersWithCars();
        this.xmlParser.marshalToFile(EXERCISE_5_FILE_PATH,customersWithCarsRootDto);
    }

    private void carsWithParts() throws JAXBException {
        CarsWithPartsRootDto carsWithPartsRootDto = this.carService.getCarsWithParts();
        this.xmlParser.marshalToFile(EXERCISE_4_FILE_PATH,carsWithPartsRootDto);
    }

    private void localSupplier() throws JAXBException {
        LocalSupplierRootDto localSupplierRootDto = this.supplierService.getAllSuppliersWithPartsCount();
        this.xmlParser.marshalToFile(EXERCISE_3_FILE_PATH,localSupplierRootDto);
    }

    private void carsMakeFromToyota() throws JAXBException {
        CarMakeFromToyotaRootDto carMakeFromToyotaRootDto = this.carService.getAllCarsMakeFromToyota();
        this.xmlParser.marshalToFile(EXERCISE_2_FILE_PATH,carMakeFromToyotaRootDto);
    }

    private void orderedCustomers() throws JAXBException {
        CustomerViewRootDto customerViewRootDto = this.customerService.getAllByBirthDate();
        this.xmlParser.marshalToFile(EXERCISE_1_FILE_PATH,customerViewRootDto);
    }

    private void seedData() throws JAXBException, FileNotFoundException {
        seedSuppliers();
        seedPart();
        seedCar();
        seedCustomer();
        seedSale();
    }

    private void seedSale() {
        this.saleService.seedSales();
    }

    private void seedCustomer() throws JAXBException, FileNotFoundException {
        CustomerSeedRootDto customerSeedRootDto = this.xmlParser.unmarshalFromFile(CUSTOMER_FILE_PATH, CustomerSeedRootDto.class);
        this.customerService.seedCustomers(customerSeedRootDto.getCustomers());
    }

    private void seedCar() throws JAXBException, FileNotFoundException {
        CarSeedRootDto carSeedRootDto = this.xmlParser.unmarshalFromFile(CAR_FILE_PATH, CarSeedRootDto.class);
        this.carService.seedCars(carSeedRootDto.getCars());
    }

    private void seedPart() throws JAXBException, FileNotFoundException {
        PartSeedRootDto partSeedRootDto = this.xmlParser.unmarshalFromFile(PART_FILE_PATH, PartSeedRootDto.class);
        this.partService.seedPart(partSeedRootDto.getPartSeedDtos());
    }

    private void seedSuppliers() throws JAXBException, FileNotFoundException {
        SupplierSeedRootDto supplierSeedRootDto = this.xmlParser.unmarshalFromFile(SUPPLIER_FILE_PATH, SupplierSeedRootDto.class);
        System.out.println();
        this.supplierService.seedSuppliers(supplierSeedRootDto.getSuppliers());

    }

    private void startProgram() {
        System.out.printf("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t%s%n", "<<< WELLCOME >>>");
        System.out.printf("%n\t\t\t\t\t\t\t\t\t\t\t\t\t%s%n%n", INFO);
        System.out.println("To seed data into base first press 0 else press query number:");
    }
}
