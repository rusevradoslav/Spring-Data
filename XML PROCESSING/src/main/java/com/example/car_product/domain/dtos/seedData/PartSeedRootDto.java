package com.example.car_product.domain.dtos.seedData;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartSeedRootDto {

    @XmlElement(name = "part")
    List<PartSeedDto> partSeedDtos;

    public PartSeedRootDto() {
    }

    public List<PartSeedDto> getPartSeedDtos() {
        return partSeedDtos;
    }

    public void setPartSeedDtos(List<PartSeedDto> partSeedDtos) {
        this.partSeedDtos = partSeedDtos;
    }
}
