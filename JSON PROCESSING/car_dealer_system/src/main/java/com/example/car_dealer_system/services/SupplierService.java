package com.example.car_dealer_system.services;

import com.example.car_dealer_system.domain.dtos.Query3.LocalSuppliersDto;
import com.example.car_dealer_system.domain.dtos.seedData.SupplierSeedDto;
import com.example.car_dealer_system.domain.entities.Supplier;

import java.util.List;

public interface SupplierService {
    void seedSuppliers(SupplierSeedDto[] supplierSeedDtos);

    Supplier getRandomSupplier();


    List<LocalSuppliersDto> getAllSuppliersWithPartsCount();
}
