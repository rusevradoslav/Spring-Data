package com.example.game_store.domain.dtos.game_dtos;

        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import javax.validation.constraints.NotNull;
        import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {


    @NotNull(message = "Id can not be null or empty!")
    @Positive(message = "Id cannot be zero or a negative number!")
    long id;
}
