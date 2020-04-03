package mostwanted.domain.dtos;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;
import mostwanted.domain.entities.Racer;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CarImportDto {

    @NotNull
    @Expose
    private String brand;
    @NotNull
    @Expose
    private String model;
    @Expose
    private BigDecimal price;
    @NotNull
    @Expose
    private Integer yearOfProduction;
    @Expose
    private double maxSpeed;
    @Expose
    private double zeroToSixty;
    @Expose
    private String racerName;
}
