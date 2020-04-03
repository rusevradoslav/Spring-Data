package com.example.car_product.domain.dtos.Query5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersWithCarsRootDto {

    @XmlElement(name = "customer")
    List<CustomersWithCarsDto> customers;

    public CustomersWithCarsRootDto() {
    }

    public List<CustomersWithCarsDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomersWithCarsDto> customers) {
        this.customers = customers;
    }
}
