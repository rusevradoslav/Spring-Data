package mostwanted.repository;

import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RacerRepository extends JpaRepository<Racer, Long> {
    Racer findFirstByName(String name);
    //TODO: Implement me
}
