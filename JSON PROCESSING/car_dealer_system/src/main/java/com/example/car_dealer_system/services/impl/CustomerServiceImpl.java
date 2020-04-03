package com.example.car_dealer_system.services.impl;

import com.example.car_dealer_system.domain.dtos.Query1.CustomerDto;
import com.example.car_dealer_system.domain.dtos.Query1.SaleDto;
import com.example.car_dealer_system.domain.dtos.Query5.CustomerWithCars;
import com.example.car_dealer_system.domain.dtos.seedData.CustomerSeedDto;
import com.example.car_dealer_system.domain.entities.Customer;
import com.example.car_dealer_system.domain.entities.Part;
import com.example.car_dealer_system.domain.entities.Sale;
import com.example.car_dealer_system.repositories.CustomerRepository;
import com.example.car_dealer_system.services.CustomerService;
import com.example.car_dealer_system.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, CustomerRepository customerRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.customerRepository = customerRepository;
    }

    @Override
    public void seedCustomers(CustomerSeedDto[] customerSeedDtos) {
        Arrays.stream(customerSeedDtos).forEach(customerSeedDto -> {

                    if (validationUtil.isValid(customerSeedDto)) {
                        if (checkIfCustomerExist(customerSeedDto)) {
                            System.out.printf("%s with that name already exist!", customerSeedDto.getName());
                        }
                        Customer customer = this.modelMapper.map(customerSeedDto, Customer.class);

                        //  String dateFromJson = String.valueOf(customerSeedDto.getBirthDate());

                        //  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

                        //  LocalDateTime date = LocalDateTime.parse(dateFromJson, formatter);

                        // customer.setBirthDate(date);

                        //if you use this method you must set the birthDate in CustomerSeedDto from LocalDateTime to String !

                        System.out.println();
                        this.customerRepository.saveAndFlush(customer);
                    } else {
                        validationUtil.getViolation(customerSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                }
        );
    }

    @Override
    public int getAll() {
        int count = (int) this.customerRepository.count();
        System.out.println();
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
    public List<CustomerDto> getAllByBirthDate() {
        List<Customer> allOrderedByBirthDateDesc = this.customerRepository.getAllByOrderByBirthDateAscYoungDriver();

        List<CustomerDto> collect = allOrderedByBirthDateDesc.stream().map(customer -> {
            CustomerDto customerDto = this.modelMapper.map(customer, CustomerDto.class);

            List<SaleDto> saleDtos = new ArrayList<>();

            List<Sale> sales = customer.getSales();

            sales.stream().forEach(sale -> {
                SaleDto sDto = new SaleDto();
                sDto.setCar(sale.getCar());
                sDto.setCustomer(sale.getCustomer());
                sDto.setDiscount(sale.getDiscount());
                saleDtos.add(sDto);
            });
            customer.setSales(sales);

            return customerDto;
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public List<CustomerWithCars> getCustomersWithCars() {
        List<CustomerWithCars> collect = this.customerRepository
                .findAll()
                .stream()
                .filter(c -> c.getSales().size() >= 1)
                .map(customer -> {
                    CustomerWithCars cwcDto = new CustomerWithCars();

                    cwcDto.setFullName(customer.getName());
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

        List<CustomerWithCars> customerWithCars = collect.stream().sorted((f, s) -> {
                    int result = s.getSpentMoney().compareTo(f.getSpentMoney());
                    if (result == 0) {
                        result = s.getBoughtCars().compareTo(f.getBoughtCars());
                    }

                    return result;
                }
        ).collect(Collectors.toList());
        return customerWithCars;
    }

    private boolean checkIfCustomerExist(CustomerSeedDto customerSeedDto) {
        Customer customer = this.customerRepository.findFirstByName(customerSeedDto.getName());

        return customer != null;
    }
}
