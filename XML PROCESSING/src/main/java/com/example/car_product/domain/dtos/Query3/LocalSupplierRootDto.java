package com.example.car_product.domain.dtos.Query3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocalSupplierRootDto {

    @XmlElement(name = "supplier")
    List<LocalSupplierDto> suppliers;

    public LocalSupplierRootDto() {
    }

    public List<LocalSupplierDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<LocalSupplierDto> suppliers) {
        this.suppliers = suppliers;
    }
}
