package softuni.exam.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import softuni.exam.models.entities.Town;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class PassengerSeedDto {


    @Expose
    @Length(min = 2)
    private String firstName;

    @Expose
    @Length(min = 2)
    private String lastName;

    @Expose
    @Positive
    private Integer age;

    @Expose
    private String phoneNumber;

    @Expose
    private String email;

    @Expose
    private String town;
}
