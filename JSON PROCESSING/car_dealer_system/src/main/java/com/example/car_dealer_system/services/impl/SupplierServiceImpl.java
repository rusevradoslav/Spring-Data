package com.example.car_dealer_system.services.impl;

import com.example.car_dealer_system.domain.dtos.Query3.LocalSuppliersDto;
import com.example.car_dealer_system.domain.dtos.seedData.SupplierSeedDto;
import com.example.car_dealer_system.domain.entities.Supplier;
import com.example.car_dealer_system.repositories.SupplierRepository;
import com.example.car_dealer_system.services.SupplierService;
import com.example.car_dealer_system.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, SupplierRepository supplierRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.supplierRepository = supplierRepository;

    }

    @Override
    public void seedSuppliers(SupplierSeedDto[] supplierSeedDtos) {
        Arrays.stream(supplierSeedDtos).forEach(supplierSeedDto -> {

                    if (this.validationUtil.isValid(supplierSeedDto)) {
                        if (checkIfExist(supplierSeedDto)) {
                            System.out.printf("%s with that name already exist!%n", supplierSeedDto.getName());
                            return;
                        }


                        Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);

                        this.supplierRepository.saveAndFlush(supplier);

                    } else {
                        this.validationUtil.getViolation(supplierSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                }
        );
    }

    @Override
    public Supplier getRandomSupplier() {
        Random random = new Random();
        long randomId = random.nextInt((int) this.supplierRepository.count()) + 1;

        Supplier supplier = this.supplierRepository.findById(randomId);
        return supplier;
    }

    @Override
    public List<LocalSuppliersDto> getAllSuppliersWithPartsCount() {
        List<LocalSuppliersDto> localSuppliersDtos = this.supplierRepository
                .getAllSuppliersWithPartsCount()
                .stream()
                .map(supplier -> {
                    LocalSuppliersDto localSuppliersDto = this.modelMapper.map(supplier,LocalSuppliersDto.class);
                    localSuppliersDto.setPartsCount(supplier.getParts().size());
                    return localSuppliersDto;
                }).collect(Collectors.toList());
        return localSuppliersDtos;
    }


    private boolean checkIfExist(SupplierSeedDto supplierSeedDto) {
        Supplier supplier = this.supplierRepository.findFirstByName(supplierSeedDto.getName());
        return supplier != null;
    }
}
