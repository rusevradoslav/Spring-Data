package com.json_exercises.product_shop.controller;

import com.google.gson.Gson;
import com.json_exercises.product_shop.models.dtos.firstQuery.ProductInRangeDto;
import com.json_exercises.product_shop.models.dtos.secondQuery.SellerWithAtLeastOneProductDto;
import com.json_exercises.product_shop.models.dtos.seedData.CategorySeedDto;
import com.json_exercises.product_shop.models.dtos.seedData.ProductSeedDto;
import com.json_exercises.product_shop.models.dtos.seedData.UserSeedDto;
import com.json_exercises.product_shop.models.dtos.thirdQuery.CategoriesByProductCountDto;
import com.json_exercises.product_shop.models.dtos.fourthQuery.UsersAndProductsDto;
import com.json_exercises.product_shop.services.CategoryService;
import com.json_exercises.product_shop.services.ProductService;
import com.json_exercises.product_shop.services.UserService;
import com.json_exercises.product_shop.utils.FileUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.json_exercises.product_shop.constants.GlobalConstants.*;

@Controller
public class AppController implements CommandLineRunner {

    private final Gson gson;
    private final FileUtil fileUtil;
    private final BufferedReader bufferedReader;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    public AppController(Gson gson, FileUtil fileUtil, BufferedReader bufferedReader, UserService userService, CategoryService categoryService, ProductService productService) {
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.bufferedReader = bufferedReader;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("To seed the data into database first pres 0 else press query number :");
        String numberOfCommand = bufferedReader.readLine();

        switch (numberOfCommand) {
            case "0":
                seedCategory();
                seedUser();
                seedProduct();
                break;
            case "1":
                productInRange();
                break;
            case "2":
                successfullySoldProduct();
                break;
            case "3":
                categoryByProductsCount();
                break;
            case "4":
                usersAndProducts();
                break;
            default:
                System.out.println("WARNING: Wrong query number!");
        }
    }

    private void usersAndProducts() throws IOException {
        UsersAndProductsDto usersAndProductsDtos = this.userService.getUsersAndProducts();
        String json = this.gson.toJson(usersAndProductsDtos);
        System.out.println();
        this.fileUtil.write(json, EXERCISE_4_FILE_PATH);

    }

    private void categoryByProductsCount() throws IOException {
        List<CategoriesByProductCountDto> categoriesByProductCountDtos = this.categoryService.getAllCategoriesByProductCount();
        String json = this.gson.toJson(categoriesByProductCountDtos);
        System.out.println();
        this.fileUtil.write(json, EXERCISE_3_FILE_PATH);

    }

    private void successfullySoldProduct() throws IOException {
        List<SellerWithAtLeastOneProductDto> allProductsWithBuyer = this.productService.getAllProductsWithBuyer();
        String json = this.gson.toJson(allProductsWithBuyer);
        this.fileUtil.write(json, EXERCISE_2_FILE_PATH);
    }

    private void productInRange() throws IOException {
        List<ProductInRangeDto> productInRangeDtos = this.productService.getProductInRange();
        String json = this.gson.toJson(productInRangeDtos);
        this.fileUtil.write(json, EXERCISE_1_FILE_PATH);

    }

    private void seedProduct() throws FileNotFoundException {
        ProductSeedDto[] productSeedDtos = this.gson.fromJson(new FileReader(PRODUCT_FILE_PATH), ProductSeedDto[].class);
        System.out.println();
        this.productService.seedProducts(productSeedDtos);
    }

    private void seedCategory() throws FileNotFoundException {
        CategorySeedDto[] categorySeedDtos = this.gson
                .fromJson(new FileReader(CATEGORY_FILE_PATH), CategorySeedDto[].class);
        System.out.println();
        this.categoryService.seedCategories(categorySeedDtos);

    }

    private void seedUser() throws FileNotFoundException {
        UserSeedDto[] userSeedDtos = this.gson
                .fromJson(new FileReader(USER_FILE_PATH), UserSeedDto[].class);

        userService.seedUsers(userSeedDtos);
    }
}
