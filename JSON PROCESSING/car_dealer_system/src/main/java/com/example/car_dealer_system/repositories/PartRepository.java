package com.example.car_dealer_system.repositories;

        import com.example.car_dealer_system.domain.entities.Car;
        import com.example.car_dealer_system.domain.entities.Part;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

        import java.math.BigDecimal;

@Repository
public interface PartRepository extends JpaRepository<Part,Long> {
    Part findFirstByNameAndPrice(String name, BigDecimal price);

    Part findFirstById(long partId);
}
