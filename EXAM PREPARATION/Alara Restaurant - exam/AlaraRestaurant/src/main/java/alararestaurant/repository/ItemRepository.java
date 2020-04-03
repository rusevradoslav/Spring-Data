package alararestaurant.repository;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findFirstByName(String name);
}
