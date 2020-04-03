package com.example.game_store.domain.dtos.game_dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameEditDto {


    @NotNull(message = "Id can not be null or empty!")
    @Positive(message = "Id cannot be zero or a negative number!")
    long id;

    @NotNull(message = "Price can not be null or empty!")
    @DecimalMin(value = "0", message = "Price cannot be zero or a negative number!")
    BigDecimal price;

    @NotNull(message = "Size can not be null or empty!")
    @Positive(message = "Size cannot be zero or a negative number!")
    double size;
}

