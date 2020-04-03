package alararestaurant.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(nullable = false,columnDefinition = "TEXT")
    private String customer;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private OrderType type;

    @ManyToOne
    private Employee employee;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
