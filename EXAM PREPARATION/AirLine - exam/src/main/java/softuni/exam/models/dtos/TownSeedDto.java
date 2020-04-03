package softuni.exam.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class TownSeedDto {

    @Expose
    @Length(min = 2)
    @NotNull
    private String name;

    @Expose
    @Positive
    @NotNull
    private Long population;

    @Expose
    @NotNull
    private String guide;
}
