package softuni.exam.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    private Rating rating;
    private String town;

    public Seller() {
    }

    @Column
    @Length(min = 2, max = 19)
    public String getFirstName() {
        return firstName;
    }

    @Column
    @Length(min = 2, max = 19)
    public String getLastName() {
        return lastName;
    }

    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public Rating getRating() {
        return rating;
    }

    @Column(nullable = false)
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
