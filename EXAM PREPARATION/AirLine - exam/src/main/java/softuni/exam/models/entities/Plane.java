package softuni.exam.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "planes")
public class Plane extends BaseEntity {

    @Column(unique = true)
    @Length(min = 5)
    private String registerNumber;
    @Column
    @Positive
    private Integer capacity;
    @Column
    @Length(min = 2)
    private String airline;
    @OneToMany(mappedBy = "plane")
    private List<Ticket> tickets;
}
