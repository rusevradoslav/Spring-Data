package softuni.exam.models.dtos.SeedSeller;

import org.hibernate.validator.constraints.Length;
import softuni.exam.models.entities.Rating;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "seller")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerSeedDto {

    @XmlElement(name = "first-name")
    private String firstName;
    @XmlElement(name = "last-name")
    private String lastName;
    @XmlElement
    private String email;
    @XmlElement
    private Rating rating;
    @XmlElement
    private String town;

    public SellerSeedDto() {
    }

    @Length(min = 2, max = 19)
    public String getFirstName() {
        return firstName;
    }

    @Length(min = 2, max = 19)
    public String getLastName() {
        return lastName;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    @NotNull
    public Rating getRating() {
        return rating;
    }

    @NotNull
    public String getTown() {
        return town;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
