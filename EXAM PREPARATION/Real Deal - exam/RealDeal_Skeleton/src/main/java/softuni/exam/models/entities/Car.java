package softuni.exam.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    private String make;
    private String model;
    private Long kilometers;
    private LocalDate registeredOn;
    private List<Picture> pictures;
    private List<Offer> offers;

    public Car() {
    }

    @Column
    @Length(min = 2, max = 19)
    public String getMake() {
        return make;
    }

    @Column
    @Length(min = 2, max = 19)
    public String getModel() {
        return model;
    }

    @Column
    @Positive
    public Long getKilometers() {
        return kilometers;
    }

    @Column
    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    @OneToMany(mappedBy = "car")
    public List<Picture> getPictures() {
        return pictures;
    }
    @OneToMany(mappedBy = "car")
    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setKilometers(Long kilometers) {
        this.kilometers = kilometers;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

}
