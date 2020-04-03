package softuni.exam.models.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PictureSeedDto {
    @Expose
    private String name;
    @Expose
    private LocalDateTime dateAndTime;
    @Expose
    private Long car;

    public PictureSeedDto() {
    }
    @Length(min = 2, max = 19)
    public String getName() {
        return name;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public Long getCar() {
        return car;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public void setCar(Long car) {
        this.car = car;
    }
}
