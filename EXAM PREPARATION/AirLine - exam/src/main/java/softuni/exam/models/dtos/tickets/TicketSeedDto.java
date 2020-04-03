package softuni.exam.models.dtos.tickets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import softuni.exam.adapters.XmlLocalDateTimeAdapter;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Town;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedDto {

    @XmlElement(name = "serial-number")
    @Length(min = 2)
    @Pattern(regexp = "[a-zA-z0-9\\s].+\\b")
    private String serialNumber;

    @XmlElement(name = "price")
    @DecimalMin(value = "0")
    private BigDecimal price;

    @XmlElement(name = "take-off")
    @XmlJavaTypeAdapter(XmlLocalDateTimeAdapter.class)
    private LocalDateTime takeOff;

    @XmlElement(name = "from-town")
    private FromTownTicketDto fromTown;

    @XmlElement(name = "to-town")
    private ToTownTicketDto toTown;

    @XmlElement(name = "passenger")
    private PassengerTicketDto passenger;

    @XmlElement(name = "plane")
    private PlaneTicketDto plane;
}
