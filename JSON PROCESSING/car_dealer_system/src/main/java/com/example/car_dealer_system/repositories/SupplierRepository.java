package com.example.car_dealer_system.repositories;

import com.example.car_dealer_system.domain.entities.Car;
import com.example.car_dealer_system.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findFirstByName(String name);

    Supplier findById(long id);

    @Query("select s from Supplier s where s.importer = false order by s.id asc ")
    List<Supplier> getAllSuppliersWithPartsCount();
}