package com.json_exercises.product_shop.services.impl;

import com.json_exercises.product_shop.models.dtos.firstQuery.ProductInRangeDto;
import com.json_exercises.product_shop.models.dtos.secondQuery.ProductWithBuyerDto;
import com.json_exercises.product_shop.models.dtos.secondQuery.SellerWithAtLeastOneProductDto;
import com.json_exercises.product_shop.models.dtos.seedData.ProductSeedDto;
import com.json_exercises.product_shop.models.entities.Category;
import com.json_exercises.product_shop.models.entities.Product;
import com.json_exercises.product_shop.models.entities.User;
import com.json_exercises.product_shop.repositories.ProductRepository;
import com.json_exercises.product_shop.services.CategoryService;
import com.json_exercises.product_shop.services.ProductService;
import com.json_exercises.product_shop.services.UserService;
import com.json_exercises.product_shop.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, ProductRepository productRepository, UserService userService, CategoryService categoryService) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedProducts(ProductSeedDto[] productSeedDtos) {

        Arrays.stream(productSeedDtos).forEach(productSeedDto -> {

            if (this.validationUtil.isValid(productSeedDto)) {
                if (chekIfProductExist(productSeedDto)) {
                    System.out.printf("%s already exist!", productSeedDto.getName());
                    return;
                }
                Product product = this.modelMapper.map(productSeedDto, Product.class);

                User seller = this.userService.getRandomUser();
                User buyer = this.userService.getRandomUser();

                product.setSeller(seller);

                Random random = new Random();
                int randomCount = random.nextInt(2);

                if (randomCount == 1) {
                    product.setBuyer(buyer);
                }

                Set<Category> categories = this.categoryService.getRandomCategories();

                product.setCategories(categories);
                System.out.println();
                this.productRepository.saveAndFlush(product);

            } else {
                this.validationUtil.getViolation(productSeedDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        });
    }

    @Override
    public List<ProductInRangeDto> getProductInRange() {
        return this.productRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal.valueOf(500), BigDecimal.valueOf(1000))
                .stream()
                .map(product -> {
                    ProductInRangeDto productInRangeDto = modelMapper.map(product, ProductInRangeDto.class);
                    productInRangeDto.setSeller(String.format("%s %s",
                            product.getSeller().getFirstName(),
                            product.getSeller().getLastName()));
                    return productInRangeDto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<SellerWithAtLeastOneProductDto> getAllProductsWithBuyer() {
        List<Product> products = this.productRepository.findByBuyerIsNotNull();
        List<SellerWithAtLeastOneProductDto> sellerWithAtLeastOneProductDtos = new ArrayList<>();

        for (Product product : products) {
            String firstName = product.getSeller().getFirstName();
            String lastName = product.getSeller().getLastName();

            List<ProductWithBuyerDto> productDto = new LinkedList<>();
            List<Product> sellerToProduct = this.productRepository.findBySellerId(product.getSeller().getId());

            for (Product p : sellerToProduct) {
                ProductWithBuyerDto pDto = new ProductWithBuyerDto();
                pDto.setName(p.getName());
                pDto.setPrice(p.getPrice());
                pDto.setBuyerFirstName(p.getBuyer().getFirstName());
                pDto.setBuyerLastName(p.getBuyer().getLastName());

                productDto.add(pDto);
            }

            SellerWithAtLeastOneProductDto sDto = new SellerWithAtLeastOneProductDto();
            sDto.setFirstName(firstName);
            sDto.setLastName(lastName);
            sDto.setSoldProducts(productDto);

            boolean isFound = false;
            for (SellerWithAtLeastOneProductDto seller : sellerWithAtLeastOneProductDtos) {
                if (seller.getLastName().equals(product.getSeller().getLastName())) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                sellerWithAtLeastOneProductDtos.add(sDto);
            }


        }
        System.out.println();
        return sellerWithAtLeastOneProductDtos;
    }

    private boolean chekIfProductExist(ProductSeedDto productSeedDto) {
        Product product = this.productRepository.findFirstByName(productSeedDto.getName());

        return product != null;
    }
}
