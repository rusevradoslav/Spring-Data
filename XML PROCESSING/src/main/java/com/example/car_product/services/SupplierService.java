package com.example.car_product.services;

import com.example.car_product.domain.dtos.Query3.LocalSupplierRootDto;
import com.example.car_product.domain.dtos.seedData.SupplierSeedDto;
import com.example.car_product.domain.entities.Supplier;

import java.util.List;

public interface SupplierService {
    void seedSuppliers(List<SupplierSeedDto> supplierSeedDtos);

    Supplier getRandomSupplier();

    LocalSupplierRootDto getAllSuppliersWithPartsCount();

}
