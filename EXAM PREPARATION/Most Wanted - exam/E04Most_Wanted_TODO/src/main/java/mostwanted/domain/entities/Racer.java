package mostwanted.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "racers")
public class Racer extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private Integer age;
    @Column
    private BigDecimal bounty;

    @ManyToOne
    private Town homeTown;

    @OneToMany(mappedBy = "racer")
    private List<Car> cars;

}
