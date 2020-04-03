package com.example.car_dealer_system.domain.entities;

import com.example.car_dealer_system.domain.dtos.Query1.SaleDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    private String name;
    private LocalDateTime birthDate;
    private boolean isYoungDriver;
    private List<Sale> sales;

    public Customer() {
    }

    @Column
    public String getName() {
        return name;
    }

    @Column
    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    @Column
    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    @OneToMany(mappedBy = "customer" ,targetEntity = Sale.class)//,fetch = FetchType.EAGER,
    public List<Sale> getSales() {
        return sales;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}
