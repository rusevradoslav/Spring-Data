package com.example.car_product.services.impl;

import com.example.car_product.domain.dtos.Query3.LocalSupplierDto;
import com.example.car_product.domain.dtos.Query3.LocalSupplierRootDto;
import com.example.car_product.domain.dtos.seedData.SupplierSeedDto;
import com.example.car_product.domain.entities.Supplier;
import com.example.car_product.repositories.SupplierRepository;
import com.example.car_product.services.SupplierService;
import com.example.car_product.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Transactional
@Service
public class SupplierServiceImpl implements SupplierService {
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(ValidationUtil validationUtil, ModelMapper modelMapper, SupplierRepository supplierRepository) {
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void seedSuppliers(List<SupplierSeedDto> suppliers) {
        suppliers.stream().forEach(supplierSeedDto -> {
            if (this.validationUtil.isValid(supplierSeedDto)) {
                if (checkIfExist(supplierSeedDto)) {
                    System.out.printf("%s with that name already exist!%n", supplierSeedDto.getName());
                    return;
                }


                Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);
                System.out.println();
                supplierRepository.saveAndFlush(supplier);

            } else {
                this.validationUtil.getViolation(supplierSeedDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }

        });
    }

    @Override
    public Supplier getRandomSupplier() {
        Random random = new Random();
        long randomId = random.nextInt((int) this.supplierRepository.count()) + 1;

        Supplier supplier = this.supplierRepository.findFirstById(randomId);
        return supplier;
    }

    @Override
    public LocalSupplierRootDto getAllSuppliersWithPartsCount() {
        LocalSupplierRootDto localSupplierRootDto = new LocalSupplierRootDto();
        List<LocalSupplierDto> localSuppliersDtos = this.supplierRepository
                .getAllSuppliersWithPartsCount()
                .stream()
                .map(supplier -> {
                    LocalSupplierDto localSuppliersDto = this.modelMapper.map(supplier,LocalSupplierDto.class);
                    localSuppliersDto.setCount(supplier.getParts().size());
                    System.out.println();
                    return localSuppliersDto;
                }).collect(Collectors.toList());

        localSupplierRootDto.setSuppliers(localSuppliersDtos);
        return localSupplierRootDto;

    }

    private boolean checkIfExist(SupplierSeedDto supplierSeedDto) {
        Supplier supplier = this.supplierRepository.findFirstByName(supplierSeedDto.getName());
        return supplier != null;
    }
}
