package com.example.car_dealer_system.repositories;

import com.example.car_dealer_system.domain.entities.Car;
import com.example.car_dealer_system.domain.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {
}
