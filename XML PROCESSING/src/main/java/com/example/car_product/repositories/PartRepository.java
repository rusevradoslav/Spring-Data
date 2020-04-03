package com.example.car_product.repositories;

import com.example.car_product.domain.entities.Car;
import com.example.car_product.domain.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

    Part findFirstByNameAndPrice(String name, BigDecimal price);

    Part findFirstById(long partId);
}
