package com.example.car_product.services.impl;

import com.example.car_product.domain.dtos.Query1.CustomerViewDto;
import com.example.car_product.domain.dtos.Query1.CustomerViewRootDto;
import com.example.car_product.domain.dtos.Query5.CustomersWithCarsDto;
import com.example.car_product.domain.dtos.Query5.CustomersWithCarsRootDto;
import com.example.car_product.domain.dtos.seedData.CustomerSeedDto;
import com.example.car_product.domain.entities.Customer;
import com.example.car_product.domain.entities.Part;
import com.example.car_product.domain.entities.Sale;
import com.example.car_product.repositories.CustomerRepository;
import com.example.car_product.repositories.SaleRepository;
import com.example.car_product.services.CustomerService;
import com.example.car_product.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;

    public CustomerServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, CustomerRepository customerRepository, SaleRepository saleRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public void seedCustomers(List<CustomerSeedDto> customerSeedDtos) {
        customerSeedDtos.stream().forEach(customerSeedDto -> {
            if (validationUtil.isValid(customerSeedDto)) {
                if (checkIfCustomerExist(customerSeedDto)) {
                    System.out.printf("%s with that name already exist!", customerSeedDto.getName());
                }
                Customer customer = this.modelMapper.map(customerSeedDto, Customer.class);

                System.out.println();

                this.customerRepository.saveAndFlush(customer);
            } else {
                validationUtil.getViolation(customerSeedDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        });

    }

    @Override
    public int getAll() {
        int count = (int) this.customerRepository.count();

        return count;
    }

    @Override
    public Customer getRandomCustomer() {
        Random random = new Random();
        long randomId = random.nextInt((int) this.customerRepository.count()) + 1;
        Customer customer = this.customerRepository.findFirstById(randomId);
        return customer;
    }

    @Override
    public CustomerViewRootDto getAllOrderedCutomers() {
        return null;
    }

    @Override
    public CustomerViewRootDto getAllByBirthDate() {
        CustomerViewRootDto customerViewRootDto = new CustomerViewRootDto();

        List<CustomerViewDto> customerViewDtos =
                this.customerRepository.getAllByOrderByBirthDateAscYoungDriver()
                        .stream()
                        .map(customer -> this.modelMapper.map(customer, CustomerViewDto.class))
                        .collect(Collectors.toList());

        customerViewRootDto.setCustomerViewDtos(customerViewDtos);
        return customerViewRootDto;
    }

    @Override
    public CustomersWithCarsRootDto getCustomersWithCars() {
        CustomersWithCarsRootDto customersWithCarsRootDto = new CustomersWithCarsRootDto();

        List<CustomersWithCarsDto> collect = this.customerRepository.findAll().stream().filter(c -> c.getSales().size() >= 1)
                .map(customer -> {
                    CustomersWithCarsDto cwcDto = new CustomersWithCarsDto();

                    cwcDto.setName(customer.getName());
                    cwcDto.setBoughtCars(customer.getSales().size());

                    BigDecimal totalMoney = new BigDecimal("0");

                    for (Sale sale : customer.getSales()) {
                        for (Part part : sale.getCar().getParts()) {
                            totalMoney = totalMoney.add(part.getPrice());
                        }
                        BigDecimal discount = totalMoney.multiply(BigDecimal.valueOf(sale.getDiscount()));
                        totalMoney = totalMoney.subtract(discount);
                    }

                    cwcDto.setSpentMoney(totalMoney);

                    return cwcDto;
                }).collect(Collectors.toList());

        List<CustomersWithCarsDto> customerWithCars = collect.stream().sorted((f, s) -> {
                    int result = s.getSpentMoney().compareTo(f.getSpentMoney());
                    if (result == 0) {
                        s.getBoughtCars().compareTo(f.getBoughtCars());
                    }
                    return result;
                }
        ).collect(Collectors.toList());
        customersWithCarsRootDto.setCustomers(customerWithCars);

        return customersWithCarsRootDto;
    }

    private boolean checkIfCustomerExist(CustomerSeedDto customerSeedDto) {
        Customer customer = this.customerRepository.findFirstByName(customerSeedDto.getName());

        return customer != null;
    }


}
