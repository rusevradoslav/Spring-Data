package com.example.car_product.domain.dtos.Query2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarMakeFromToyotaRootDto {

    @XmlElement(name = "car")
    List<CarMakeFromToyotaDto> cars;

    public CarMakeFromToyotaRootDto() {
    }

    public List<CarMakeFromToyotaDto> getCars() {
        return cars;
    }

    public void setCars(List<CarMakeFromToyotaDto> cars) {
        this.cars = cars;
    }
}
