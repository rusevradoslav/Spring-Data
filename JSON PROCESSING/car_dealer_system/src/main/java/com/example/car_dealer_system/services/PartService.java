package com.example.car_dealer_system.services;

import com.example.car_dealer_system.domain.dtos.seedData.PartSeedDto;
import com.example.car_dealer_system.domain.entities.Part;

import java.util.List;
import java.util.Set;

public interface PartService {
    void seedParts(PartSeedDto[] partSeedDtos);

    Set<Part> getBetween10And20RandomParts();
}
