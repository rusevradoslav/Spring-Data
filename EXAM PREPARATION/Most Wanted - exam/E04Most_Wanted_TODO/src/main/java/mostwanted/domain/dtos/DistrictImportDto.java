package mostwanted.domain.dtos;

        import com.google.gson.annotations.Expose;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class DistrictImportDto {

    @Expose
    @NotNull
    private String name;
    @Expose
    private String townName;
}
