package com.example.car_product.repositories;

import com.example.car_product.domain.dtos.Query1.CustomerViewDto;
import com.example.car_product.domain.entities.Car;
import com.example.car_product.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findFirstByName(String name);

    Customer findFirstById(long randomId);

    @Query("SELECT c FROM Customer AS c " +
            "ORDER BY c.birthDate, c.youngDriver")
    List<Customer> getAllByOrderByBirthDateAscYoungDriver();
}
