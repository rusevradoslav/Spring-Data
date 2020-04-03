package alararestaurant.repository;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findFirstByName(String name);
}
