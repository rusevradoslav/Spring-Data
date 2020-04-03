package softuni.exam.models.dtos.plane;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import softuni.exam.models.entities.Ticket;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "plane")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneSeedDto {


    @XmlElement(name = "register-number")
    @Length(min = 5)
    private String registerNumber;

    @XmlElement
    @Positive
    private Integer capacity;

    @XmlElement
    @Length(min = 2)
    private String airline;

}
