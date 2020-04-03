package com.json_exercises.product_shop.services;


import com.json_exercises.product_shop.models.dtos.seedData.CategorySeedDto;
import com.json_exercises.product_shop.models.dtos.thirdQuery.CategoriesByProductCountDto;
import com.json_exercises.product_shop.models.entities.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories(CategorySeedDto[] categorySeedDtos);

    Set<Category> getRandomCategories();

    List<CategoriesByProductCountDto> getAllCategoriesByProductCount();
}
