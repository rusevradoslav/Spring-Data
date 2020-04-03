package mostwanted.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "races")
public class Race extends BaseEntity {

    @Column(nullable = false)
    @Min(value = 0)
    private Integer laps;

    @ManyToOne
    private District district;

    @OneToMany(mappedBy = "race")
    private List<RaceEntry> entries;


}
