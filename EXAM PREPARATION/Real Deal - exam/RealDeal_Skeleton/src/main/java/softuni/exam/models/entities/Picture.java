package softuni.exam.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

    private String name;
    private LocalDateTime dateAndTime;
    private Car car;
    private List<Offer> offers;

    public Picture() {
    }

    @Column(unique = true)
    @Length(min = 2, max = 19)
    public String getName() {
        return name;
    }

    @Column
    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    @ManyToOne
    @JoinColumn(name = "car_id")
    public Car getCar() {
        return car;
    }

    @ManyToMany(mappedBy = "pictures")
    public List<Offer> getOffers() {
        return offers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
