package softuni.exam.models.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Date;

public class CarSeedDto {
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private Long kilometers;
    @Expose
    private LocalDate registeredOn;

    public CarSeedDto() {
    }
    @Length(min = 2, max = 19)
    public String getMake() {
        return make;
    }
    @Length(min = 2, max = 19)
    public String getModel() {
        return model;
    }

    @Positive
    public Long getKilometers() {
        return kilometers;
    }

    public LocalDate getRegisteredOn() {
        return registeredOn;
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
