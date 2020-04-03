package mostwanted.domain.dtos.races;

import lombok.Data;
import lombok.NoArgsConstructor;
import mostwanted.domain.entities.District;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@XmlRootElement(name = "race")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceImportDto {

    @NotNull
    @Min(0)
    @XmlElement
    private Integer laps;

    @XmlElement(name = "district-name")
    private String district;

    @XmlElement(name = "entries")
    private EntryImportRootDto entries;
}
