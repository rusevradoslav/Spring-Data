package alararestaurant.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Length(min = 3, max = 30)
    private String name;
    @ManyToOne
    private Category category;

    @Column(nullable = false)
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems;
}
