package alararestaurant.domain.entities;

        import lombok.Data;
        import lombok.NoArgsConstructor;
        import org.hibernate.validator.constraints.Length;

        import javax.persistence.*;
        import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "postions")
public class Position extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Length(min = 3, max = 30)
    private String name;

    @OneToMany(mappedBy = "position")
    private List<Employee> employees;

}
