package com.json_exercises.product_shop.services.impl;

import com.json_exercises.product_shop.models.dtos.seedData.ProductSeedDto;
import com.json_exercises.product_shop.models.dtos.seedData.UserSeedDto;
import com.json_exercises.product_shop.models.entities.Product;
import com.json_exercises.product_shop.models.entities.User;
import com.json_exercises.product_shop.models.dtos.fourthQuery.ProductsPerUserDto;
import com.json_exercises.product_shop.models.dtos.fourthQuery.UsersAndProductsDto;
import com.json_exercises.product_shop.models.dtos.fourthQuery.UsersWithSoldProductsDto;
import com.json_exercises.product_shop.repositories.UserRepository;
import com.json_exercises.product_shop.services.UserService;
import com.json_exercises.product_shop.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserRepository userRepository;

    public UserServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void seedUsers(UserSeedDto[] userSeedDtos) {
        for (UserSeedDto userSeedDto : userSeedDtos) {
            if (validationUtil.isValid(userSeedDto)) {
                if (checkIfUserExist(userSeedDto)) {
                    System.out.printf("%s %s already exist.%n", userSeedDto.getFirstName(), userSeedDto.getLastName());
                    return;
                } else {

                    User user = this.modelMapper.map(userSeedDto, User.class);
                    userRepository.saveAndFlush(user);

                }
            } else {
                this.validationUtil.getViolation(userSeedDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }

        }
        addFriends();
    }

    @Override
    public User getRandomUser() {
        Random random = new Random();
        long randomId = random.nextInt((int) this.userRepository.count()) + 1;

        User randomUser = this.userRepository.getOne(randomId);

        System.out.println();

        return randomUser;
    }

    private void addFriends() {
        Random random = new Random();
        int randomCounter = random.nextInt(3) + 1;


        for (User user : userRepository.findAll()) {

            Set<User> friends = new HashSet<>();

            for (int i = 0; i < randomCounter; i++) {

                long randomId = random.nextInt((int) this.userRepository.count()) + 1;

                User randomUser = this.userRepository.findById(randomId).orElse(null);

                if (!(user.getLastName().equals(randomUser.getLastName()))) {
                    friends.add(randomUser);
                }
            }
            user.setFriends(friends);
            this.userRepository.saveAndFlush(user);
        }

    }

    private boolean checkIfUserExist(UserSeedDto userSeedDto) {
        User user = this.userRepository
                .findFirstByFirstNameAndLastName(userSeedDto.getFirstName(), userSeedDto.getLastName());

        return user != null;
    }

    @Override
    public UsersAndProductsDto getUsersAndProducts() {
        List<User> users = this.userRepository.getAllUsersWithAtLeastOneSoldProduct();

        System.out.println();

        UsersAndProductsDto upDto = new UsersAndProductsDto();

        upDto.setUsersCount(users.size());

        List<UsersWithSoldProductsDto> usersWithSoldProductsDto = new ArrayList<>();

        for (User user : users) {
            UsersWithSoldProductsDto userWithProduct = new UsersWithSoldProductsDto();

            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            int age = user.getAge();

            userWithProduct.setFirstName(firstName);
            userWithProduct.setLastName(lastName);
            userWithProduct.setAge(age);


            Set<Product> products = user.getProductSells();

            int count = products.size();
            List<ProductsPerUserDto> productsPerUserDtoList = new ArrayList<>();
            for (int i = 0; i < count; i++) {

                ProductsPerUserDto productsPerUserDto = new ProductsPerUserDto();

                productsPerUserDto.setCount(count);

                List<ProductSeedDto> productSeedDtos = new ArrayList<>();

                for (Product product : products) {
                    ProductSeedDto productSeedDto = new ProductSeedDto();

                    productSeedDto.setName(product.getName());
                    productSeedDto.setPrice(product.getPrice());

                    productSeedDtos.add(productSeedDto);

                }
                productsPerUserDto.setProducts(productSeedDtos);

                productsPerUserDtoList.add(productsPerUserDto);
            }



            userWithProduct.setSoldProducts(productsPerUserDtoList);


            usersWithSoldProductsDto.add(userWithProduct);
        }
        upDto.setUsers(usersWithSoldProductsDto);

        return upDto;
    }
}
