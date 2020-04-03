package com.json_exercises.product_shop.repositories;

        import com.json_exercises.product_shop.models.entities.Product;
        import com.json_exercises.product_shop.models.entities.User;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.query.Param;
        import org.springframework.stereotype.Repository;

        import java.math.BigDecimal;
        import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findFirstByName(String name);

    List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal f, BigDecimal s);

    @Query("select p from Product p where p.buyer.id is not null" +
            " group by p.buyer " +
            "order by p.seller.lastName , p.seller.firstName")
    List<Product> findByBuyerIsNotNull();

    @Query("select p from Product p where p.seller.id = :sellerId " +
            "order by p.buyer.firstName,p.seller.lastName")
    List<Product> findBySellerId(@Param(value = "sellerId") long id);
}
