package alararestaurant.domain.dtos.seedOrders;

import alararestaurant.adapters.XmlLocalDateTimeAdapter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderSeedDto {


    @XmlElement
    @NotNull
    private String customer;

    @XmlElement
    @NotNull
    @Length(min = 3, max = 30)
    private String employee;

    @XmlElement(name = "date-time")
    @XmlJavaTypeAdapter(XmlLocalDateTimeAdapter.class)
    @NotNull
    private LocalDateTime dateTime;

    @XmlElement
    @NotNull
    private String type;

    @XmlElement(name = "items")
    private OrderItemSeedRootDto items;
}
