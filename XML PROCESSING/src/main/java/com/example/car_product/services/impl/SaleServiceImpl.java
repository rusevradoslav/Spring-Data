package com.example.car_product.services.impl;

import com.example.car_product.domain.entities.Car;
import com.example.car_product.domain.entities.Customer;
import com.example.car_product.domain.entities.Sale;
import com.example.car_product.repositories.SaleRepository;
import com.example.car_product.services.CarService;
import com.example.car_product.services.CustomerService;
import com.example.car_product.services.SaleService;
import com.example.car_product.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;


@Transactional
@Service
public class SaleServiceImpl implements SaleService {
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final SaleRepository saleRepository;
    private final CarService carService;
    private final CustomerService customerService;

    public SaleServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, SaleRepository saleRepository, CarService carService, CustomerService customerService) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.saleRepository = saleRepository;
        this.carService = carService;
        this.customerService = customerService;
    }

    @Override
    public void seedSales() {
        if (this.saleRepository.count() != 0) {
            return;
        }
        for (int i = 1; i <= customerService.getAll(); i++) {

            Sale sale = new Sale();

            Customer customer = this.customerService.getRandomCustomer();
            Car car = this.carService.getRandomCar();

            sale.setCar(car);
            sale.setCustomer(customer);

            if (customer.getId() != i) {
                if (customer.isYoungDriver()) {

                    Double extraDiscount = getRandomDiscount() + 0.05;
                    double value = new BigDecimal(String.valueOf(extraDiscount)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                    sale.setDiscount(value);
                    saleRepository.saveAndFlush(sale);
                } else {
                    sale.setDiscount(getRandomDiscount());
                    saleRepository.saveAndFlush(sale);

                }
            } else {
                i--;
            }
        }

    }

    private Double getRandomDiscount() {
        Random random = new Random();
        Double[] discounts = {0D, 0.05, 0.10, 0.15, 0.20, 0.30, 0.40, 0.50};
        return discounts[random.nextInt(discounts.length)];
    }
}
