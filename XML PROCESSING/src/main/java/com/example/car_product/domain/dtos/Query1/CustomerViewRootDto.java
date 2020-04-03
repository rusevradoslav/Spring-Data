package com.example.car_product.domain.dtos.Query1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerViewRootDto {

    @XmlElement(name = "customer")
    List<CustomerViewDto> customerViewDtos;


    public CustomerViewRootDto() {

    }

    public List<CustomerViewDto> getCustomerViewDtos() {
        return customerViewDtos;
    }

    public void setCustomerViewDtos(List<CustomerViewDto> customerViewDtos) {
        this.customerViewDtos = customerViewDtos;
    }
}
