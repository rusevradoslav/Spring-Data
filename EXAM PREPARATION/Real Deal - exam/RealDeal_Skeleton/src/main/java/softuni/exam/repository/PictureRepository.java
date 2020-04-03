package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Picture;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    Picture findFirstByName(String name);

    Picture findFirstById(long randomId);

    List<Picture> findAllByCar_Id(long carId);

    List<Picture> findAllByCar(Car car);
}
