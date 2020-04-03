package com.example.car_dealer_system.repositories;

import com.example.car_dealer_system.domain.entities.Car;
import com.example.car_dealer_system.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findFirstByNameAndBirthDate(String name, LocalDate birthDate);

    Customer findFirstByName(String name);

    Customer findFirstById(long randomId);

    @Query("SELECT c FROM Customer AS c " +
            "ORDER BY c.birthDate, c.youngDriver")
    List<Customer> getAllByOrderByBirthDateAscYoungDriver();


}
