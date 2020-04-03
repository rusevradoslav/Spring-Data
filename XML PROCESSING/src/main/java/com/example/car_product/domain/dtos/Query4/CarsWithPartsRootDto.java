package com.example.car_product.domain.dtos.Query4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsWithPartsRootDto {

    @XmlElement(name = "car")
    List<CarsWithPartsDto> cars;

    public CarsWithPartsRootDto() {
    }

    public List<CarsWithPartsDto> getCars() {
        return cars;
    }

    public void setCars(List<CarsWithPartsDto> cars) {
        this.cars = cars;
    }
}
