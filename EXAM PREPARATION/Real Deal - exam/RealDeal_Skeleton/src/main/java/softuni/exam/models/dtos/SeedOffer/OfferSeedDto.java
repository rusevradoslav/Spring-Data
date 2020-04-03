package softuni.exam.models.dtos.SeedOffer;

import org.hibernate.validator.constraints.Length;
import softuni.exam.adapters.XmlLocalDateTimeAdapter;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Seller;

import javax.validation.constraints.DecimalMin;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedDto {

    @XmlElement
    private BigDecimal price;
    @XmlElement
    private String description;
    @XmlElement(name = "has-gold-status")
    private boolean hasGoldStatus;
    @XmlElement(name = "added-on")
    @XmlJavaTypeAdapter(XmlLocalDateTimeAdapter.class)
    private LocalDateTime addedOn;
    @XmlElement
    private CarOfferDto car;
    @XmlElement
    private CarOfferDto seller;

    public OfferSeedDto() {
    }

    @DecimalMin(value = "0")
    public BigDecimal getPrice() {
        return price;
    }
    @Length(min = 5)
    public String getDescription() {
        return description;
    }

    public boolean isHasGoldStatus() {
        return hasGoldStatus;
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public CarOfferDto getCar() {
        return car;
    }

    public CarOfferDto getSeller() {
        return seller;
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

    public void setCar(CarOfferDto car) {
        this.car = car;
    }

    public void setSeller(CarOfferDto seller) {
        this.seller = seller;
    }
}
