package softuni.exam.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {
    private BigDecimal price;
    private String description;
    private boolean hasGoldStatus;
    private LocalDateTime addedOn;
    private Car car;
    private Seller seller;
    private List<Picture> pictures;

    public Offer() {
    }

    @Column
    @DecimalMin(value = "0.01")
    public BigDecimal getPrice() {
        return price;
    }

    @Column(columnDefinition = "TEXT")
    @Length(min = 5)
    public String getDescription() {
        return description;
    }

    @Column
    public boolean isHasGoldStatus() {
        return hasGoldStatus;
    }

    @Column
    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    @ManyToOne
    @JoinColumn(name = "car_id")
    public Car getCar() {
        return car;
    }

    @ManyToOne
    @JoinColumn(name = "seller_id")
    public Seller getSeller() {
        return seller;
    }

    @ManyToMany()
    @JoinTable(name = "offers_pictures",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "picture_id"))
    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHasGoldStatus(boolean hasGoldStatus) {
        this.hasGoldStatus = hasGoldStatus;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
