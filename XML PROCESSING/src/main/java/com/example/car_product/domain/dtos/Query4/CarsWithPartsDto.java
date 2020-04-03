package com.example.car_product.domain.dtos.Query4;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsWithPartsDto {

    @XmlAttribute
    private String make;
    @XmlAttribute
    private String model;
    @XmlAttribute(name = "travelled-distance")
    private long traveledDistance;
    @XmlElement(name = "parts")
    private PartsRootDto partsRootDto;


    public CarsWithPartsDto() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getTraveledDistance() {
        return traveledDistance;
    }

    public void setTraveledDistance(long traveledDistance) {
        this.traveledDistance = traveledDistance;
    }

    public PartsRootDto getPartsRootDto() {
        return partsRootDto;
    }

    public void setPartsRootDto(PartsRootDto partsRootDto) {
        this.partsRootDto = partsRootDto;
    }
}
