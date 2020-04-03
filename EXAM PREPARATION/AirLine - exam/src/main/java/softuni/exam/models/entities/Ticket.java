package softuni.exam.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    @Column(unique = true)
    @Length(min = 2)
    private String serialNumber;
    @Column
    @DecimalMin(value = "0")
    private BigDecimal price;
    @Column
    private LocalDateTime takeOff;

    @ManyToOne
    private Town fromTown;
    @ManyToOne
    private Town toTown;
    @ManyToOne
    private Passenger passenger;
    @ManyToOne
    private Plane plane;
}
