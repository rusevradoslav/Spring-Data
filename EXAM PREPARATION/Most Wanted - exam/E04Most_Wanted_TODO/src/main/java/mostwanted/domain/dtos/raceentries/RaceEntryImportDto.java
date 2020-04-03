package mostwanted.domain.dtos.raceentries;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Data
@NoArgsConstructor
@XmlRootElement(name = "race-entry")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntryImportDto {

    @XmlAttribute(name = "has-finished")
    private boolean hasFinished;
    @XmlAttribute(name = "finish-time")
    private double finishTime;
    @XmlAttribute(name = "car-id")
    private Long car;
    @XmlElement
    private String racer;
}
