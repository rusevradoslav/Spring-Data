package com.example.car_dealer_system.repositories;

import com.example.car_dealer_system.domain.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findFirstByMakeAndModelAndTravelledDistance(String make, String model, Long distance);

    Car findFirstById(long randomId);

    @Query("select c from Car c where c.make =:mark order by c.model asc,c.travelledDistance desc")
    List<Car> findAllCarsMadeByToyota(@Param(value = "mark")String mark);
}
