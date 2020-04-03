package softuni.exam.domain.dtos;


import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import softuni.exam.domain.entities.Position;

import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static javax.persistence.EnumType.STRING;

public class PlayerSeedDto {

    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int number;
    @Expose
    private BigDecimal salary;
    @Expose
    private Position position;
    @Expose
    private PictureSeedDto picture;
    @Expose
    private TeamSeedDto team;

    public PlayerSeedDto() {
    }

    @NotNull
    public String getFirstName() {
        return firstName;
    }

    @NotNull
    @Length(min = 3 , max = 15)
    public String getLastName() {
        return lastName;
    }

    @NotNull
    @Min(1)
    @Max(99)
    public int getNumber() {
        return number;
    }

    @NotNull
    @Min(value = 0)
    public BigDecimal getSalary() {
        return salary;
    }

    @Enumerated(STRING)
    @NotNull
    public Position getPosition() {
        return position;
    }

    @NotNull
    public PictureSeedDto getPicture() {
        return picture;
    }

    @NotNull
    public TeamSeedDto getTeam() {
        return team;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPicture(PictureSeedDto picture) {
        this.picture = picture;
    }

    public void setTeam(TeamSeedDto team) {
        this.team = team;
    }
}
