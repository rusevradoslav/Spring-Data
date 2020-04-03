package com.example.game_store.domain.dtos.game_dtos;

        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;
        import lombok.RequiredArgsConstructor;
        import org.hibernate.validator.constraints.Length;

        import javax.validation.constraints.*;
        import java.math.BigDecimal;
        import java.time.LocalDate;
        import java.time.format.DateTimeFormatter;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameAddDto {

    @NotNull(message = "Title can not be null or empty!")
    @Pattern(regexp = "(^[A-Z].+)", message = "Title must begin with uppercase letter!")
    @Length(min = 3, max = 100, message = "Tittle size must be between 3 and 100 characters!")
    String title;

    @NotNull(message = "Price can not be null or empty!")
    @DecimalMin(value = "0", message = "Price cannot be zero or a negative number!")
    BigDecimal price;

    @NotNull(message = "Size can not be null or empty!")
    @Positive(message = "Size cannot be zero or a negative number!")
    double size;

    @NotNull(message = "Trailer can not be null or empty!")
    @Size(min = 11,max = 11, message = "Trailer must be exactly 11 symbols!")
    String trailer;

    @Pattern(regexp = "^https:\\/\\/.+|^http:\\/\\/.+",
            message = "Thumbnail must begin with \"https://\" or \"http://\".")
    String thumbnailURL;

    @Size(min = 20,message = "Description size must be at least 20 symbols")
    @NotNull(message = "Description can not be null or empty!")
    String description;

    @NotNull(message = "Date can not be null or empty!")
    LocalDate release_date;
}
