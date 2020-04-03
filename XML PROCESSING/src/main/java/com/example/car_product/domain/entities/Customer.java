package com.example.car_product.domain.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "customer")
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
