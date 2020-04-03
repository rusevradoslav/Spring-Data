package alararestaurant.repository;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Position findFirstByName(String position);

    Position findPositionByName(String burger_flipper);
}
