package softuni.exam.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "passengers")
public class Passenger extends BaseEntity {

    @Column
    @Length(min = 2)
    private String firstName;
    @Column
    @Length(min = 2)
    private String lastName;
    @Column
    @Positive
    private Integer age;
    @Column
    private String phoneNumber;
    @Column(unique = true)
    private String email;

    @ManyToOne
    private Town town;
    @OneToMany(mappedBy = "passenger")
    private List<Ticket> tickets;
}
