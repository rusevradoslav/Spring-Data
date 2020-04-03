package alararestaurant.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @Column(nullable = false)
    @Length(min = 3, max = 30)
    private String name;

    @Column(nullable = false)
    @Min(15)
    @Max(80)
    private Integer age;

    @ManyToOne
    private Position position;

    @OneToMany(mappedBy = "employee")
    List<Order> orders;

}
