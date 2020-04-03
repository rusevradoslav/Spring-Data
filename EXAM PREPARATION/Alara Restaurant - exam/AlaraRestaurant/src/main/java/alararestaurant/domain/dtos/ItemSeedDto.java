package alararestaurant.domain.dtos;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ItemSeedDto {

    @Expose
    @NotNull
    @Length(min = 3, max = 30)
    private String name;
    @Expose
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal price;
    @Expose
    @NotNull
    @Length(min = 3,max = 30)
    private String category;

}
