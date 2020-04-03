package com.example.car_product.repositories;

import com.example.car_product.domain.entities.Car;
import com.example.car_product.domain.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
