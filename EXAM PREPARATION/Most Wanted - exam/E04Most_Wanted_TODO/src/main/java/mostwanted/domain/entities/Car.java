package mostwanted.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    @Column
    private BigDecimal price;
    @Column(nullable = false)
    private Integer yearOfProduction;
    @Column
    private double maxSpeed;
    @Column
    private double zeroToSixty;
    @ManyToOne
    private Racer racer;

}
