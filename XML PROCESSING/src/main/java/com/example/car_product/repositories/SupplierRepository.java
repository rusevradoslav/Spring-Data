package com.example.car_product.repositories;

import com.example.car_product.domain.entities.Car;
import com.example.car_product.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findFirstByName(String name);

    Supplier findFirstById(long id);

    @Query("select s from Supplier s where s.importer = false order by s.id asc ")
    List<Supplier> getAllSuppliersWithPartsCount();
}
