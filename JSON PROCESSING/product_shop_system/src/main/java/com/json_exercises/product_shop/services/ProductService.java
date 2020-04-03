package com.json_exercises.product_shop.services;


import com.json_exercises.product_shop.models.dtos.firstQuery.ProductInRangeDto;
import com.json_exercises.product_shop.models.dtos.secondQuery.SellerWithAtLeastOneProductDto;
import com.json_exercises.product_shop.models.dtos.seedData.ProductSeedDto;

import java.util.List;

public interface ProductService {
    void seedProducts(ProductSeedDto[] productSeedDtos);

    List<ProductInRangeDto> getProductInRange();

    List<SellerWithAtLeastOneProductDto> getAllProductsWithBuyer();
}
