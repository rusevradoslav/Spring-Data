package softuni.exam.models.dtos.tickets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "from-town")
@XmlAccessorType(XmlAccessType.FIELD)
public class FromTownTicketDto {

    @XmlElement(name = "name")
    @Length(min = 2)
    private String name;
}
