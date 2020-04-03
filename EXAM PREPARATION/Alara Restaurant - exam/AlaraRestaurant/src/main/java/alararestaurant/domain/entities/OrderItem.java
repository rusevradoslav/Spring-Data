package alararestaurant.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    private Order order;

    @ManyToOne
    private Item item;

    @Column(nullable = false)
    @Min(1)
    @Positive
    private Integer quantity;
}
