package com.json_exercises.product_shop.services.impl;

import com.json_exercises.product_shop.models.dtos.seedData.CategorySeedDto;
import com.json_exercises.product_shop.models.dtos.thirdQuery.CategoriesByProductCountDto;
import com.json_exercises.product_shop.models.entities.Category;
import com.json_exercises.product_shop.models.entities.Product;
import com.json_exercises.product_shop.repositories.CategoryRepository;
import com.json_exercises.product_shop.services.CategoryService;
import com.json_exercises.product_shop.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories(CategorySeedDto[] categorySeedDtos) {
        for (CategorySeedDto categorySeedDto : categorySeedDtos) {
            if (validationUtil.isValid(categorySeedDto)) {
                if (categoryExist(categorySeedDto)) {
                    System.out.printf("%s already exist!", categorySeedDto.getName());
                    return;
                }
                Category category = this.modelMapper.map(categorySeedDto, Category.class);

                this.categoryRepository.saveAndFlush(category);


            } else {
                this.validationUtil.getViolation(categorySeedDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        }

    }

    @Override
    public Set<Category> getRandomCategories() {
        Random random = new Random();
        Set<Category> randomCategories = new HashSet<>();
        int randomCounter = random.nextInt(3) + 1;

        for (int i = 0; i < randomCounter; i++) {

            long randomId = random.nextInt((int) this.categoryRepository.count()) + 1;

            Category category = this.categoryRepository.getOne(randomId);

            randomCategories.add(category);

        }
        return randomCategories;
    }

    @Override
    public List<CategoriesByProductCountDto> getAllCategoriesByProductCount() {
        List<CategoriesByProductCountDto> categoriesByProductCountDtos = new ArrayList<>();

        List<Category> categories = this.categoryRepository.findAllCategoriesByProductsCount();

        for (Category category : categories) {
            CategoriesByProductCountDto cDto = new CategoriesByProductCountDto();


            BigDecimal sum = category.getProducts().stream().map(Product::getPrice).reduce(BigDecimal::add).orElse(null);

            BigDecimal size = BigDecimal.valueOf(category.getProducts().size());
            BigDecimal avg = sum.divide(size, 6, RoundingMode.DOWN);

            cDto.setCategory(category.getName());
            cDto.setProductCount(category.getProducts().size());
            cDto.setTotalRevenue(sum);
            cDto.setAveragePrice(avg);

            categoriesByProductCountDtos.add(cDto);

        }

        return categoriesByProductCountDtos;
    }

    private boolean categoryExist(CategorySeedDto categorySeedDto) {
        Category category = this.categoryRepository.findFirstByName(categorySeedDto.getName());

        return category != null;
    }
}
