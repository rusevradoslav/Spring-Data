package com.json_exercises.product_shop.repositories;

import com.json_exercises.product_shop.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findFirstByName(String name);

    @Query("select c from Category as c group by c.id order by c.products.size desc ")
    List<Category> findAllCategoriesByProductsCount();
}
