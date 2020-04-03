package hiberspring.repository;

import hiberspring.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findFirstByNameAndClients(String name, Integer clients);
}
