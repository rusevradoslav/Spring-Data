package com.example.car_product.domain.dtos.Query1;

import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerViewDto {

    @XmlElement
    private long id;
    @XmlElement
    private String name;
    @XmlElement(name= "birth-date")
    private LocalDateTime birthDate;
    @XmlElement(name = "is-youn-driver")
    private boolean isYoungDriver;

    public CustomerViewDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
