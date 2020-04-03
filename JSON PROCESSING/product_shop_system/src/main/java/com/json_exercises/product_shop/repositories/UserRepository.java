package com.json_exercises.product_shop.repositories;

import com.json_exercises.product_shop.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByFirstNameAndLastName(String firstName, String lastName);


    @Query("select u from User u where u.productSells.size > 0" +
            " group by u.lastName order by u.productSells.size desc , u.lastName asc ")
    List<User> getAllUsersWithAtLeastOneSoldProduct();

}
