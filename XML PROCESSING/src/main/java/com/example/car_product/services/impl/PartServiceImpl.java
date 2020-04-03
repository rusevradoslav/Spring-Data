package com.example.car_product.services.impl;

import com.example.car_product.domain.dtos.seedData.PartSeedDto;
import com.example.car_product.domain.entities.Part;
import com.example.car_product.domain.entities.Supplier;
import com.example.car_product.repositories.PartRepository;
import com.example.car_product.services.PartService;
import com.example.car_product.services.SupplierService;
import com.example.car_product.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Transactional
@Service
public class PartServiceImpl implements PartService {
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final PartRepository partRepository;
    private final Random random;
    private final SupplierService supplierService;

    public PartServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, PartRepository partRepository, Random random, SupplierService supplierService) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.partRepository = partRepository;
        this.random = random;
        this.supplierService = supplierService;
    }


    @Override
    public void seedPart(List<PartSeedDto> partSeedDtos) {
        for (PartSeedDto partSeedDto : partSeedDtos) {
            System.out.println();
            if (validationUtil.isValid(partSeedDto)) {
                if (checkIfExist(partSeedDto)) {
                    System.out.printf("%s with that name already exist.%n", partSeedDto.getName());
                    continue;
                }
                Part part = this.modelMapper.map(partSeedDto, Part.class);
                Supplier supplier = this.supplierService.getRandomSupplier();
                part.setSupplier(supplier);

             this.partRepository.saveAndFlush(part);

            } else {
                this.validationUtil.getViolation(partSeedDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        }

    }

    @Override
    public Set<Part> getBetween10And20RandomParts() {
        Random random = new Random();
        int randomCounter = random.ints(10, (20 + 1)).findFirst().getAsInt();

        Set<Part> resultList = new HashSet<>();

        for (int i = 0; i < randomCounter; i++) {
            long partId = random.nextInt((int) this.partRepository.count()) + 1;
            Part part = this.partRepository.findFirstById(partId);
            resultList.add(part);
        }
        System.out.println();
        return resultList;
    }

    private boolean checkIfExist(PartSeedDto partSeedDto) {
        Part part = this.partRepository.findFirstByNameAndPrice(partSeedDto.getName(), partSeedDto.getPrice());

        return part != null;
    }
}
