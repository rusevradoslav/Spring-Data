package alararestaurant.domain.dtos;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EmployeeSeedDto {

    @Expose
    @NotNull
    @Length(min = 3, max = 30)
    private String name;
    @NotNull

    @Min(15)
    @Max(80)
    @Expose
    private Integer age;

    @Expose
    @Length(min = 3, max = 30)
    @NotNull
    private String position;
}
