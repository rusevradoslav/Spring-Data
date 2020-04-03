package com.json_exercises.product_shop.services;


import com.json_exercises.product_shop.models.dtos.fourthQuery.UsersAndProductsDto;
import com.json_exercises.product_shop.models.dtos.seedData.UserSeedDto;
import com.json_exercises.product_shop.models.entities.User;

public interface UserService  {

    void seedUsers(UserSeedDto[] userSeedDtos);

    User getRandomUser();

    UsersAndProductsDto getUsersAndProducts();

}
