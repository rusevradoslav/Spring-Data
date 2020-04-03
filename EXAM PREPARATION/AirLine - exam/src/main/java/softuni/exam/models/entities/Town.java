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
@Table(name = "towns")
public class Town extends BaseEntity {

    @Column(unique = true)
    @Length(min = 2)
    private String name;
    @Column
    @Positive
    private Long population;
    @Column(columnDefinition = "TEXT")
    private String guide;
    @OneToMany(mappedBy = "town")
    private List<Passenger> passengers;
}
