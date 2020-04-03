package com.example.car_dealer_system.services.impl;

import com.example.car_dealer_system.domain.dtos.seedData.PartSeedDto;
import com.example.car_dealer_system.domain.entities.Part;
import com.example.car_dealer_system.domain.entities.Supplier;
import com.example.car_dealer_system.repositories.PartRepository;
import com.example.car_dealer_system.services.PartService;
import com.example.car_dealer_system.services.SupplierService;
import com.example.car_dealer_system.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.util.*;

@Service
@Transactional
public class PartServiceImpl implements PartService {
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final PartRepository partRepository;
    private final SupplierService supplierService;

    public PartServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, PartRepository partRepository, SupplierService supplierService) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.partRepository = partRepository;
        this.supplierService = supplierService;
    }

    @Override
    public void seedParts(PartSeedDto[] partSeedDtos) {
        Arrays.stream(partSeedDtos).forEach(partSeedDto -> {
            if (validationUtil.isValid(partSeedDto)) {
                if (checkIfExist(partSeedDto)) {
                    System.out.printf("%s with that name already exist.%n", partSeedDto.getName());
                    return;
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


        });
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
