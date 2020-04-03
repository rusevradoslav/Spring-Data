package com.example.car_product.services;

import com.example.car_product.domain.dtos.seedData.PartSeedDto;
import com.example.car_product.domain.entities.Part;

import java.util.List;
import java.util.Set;

public interface PartService {
    void seedPart(List<PartSeedDto> partSeedDtos);

    Set<Part> getBetween10And20RandomParts();
}
