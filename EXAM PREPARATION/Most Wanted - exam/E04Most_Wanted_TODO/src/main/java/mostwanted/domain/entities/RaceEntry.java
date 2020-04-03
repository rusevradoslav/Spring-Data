package mostwanted.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "race_entries")
public class RaceEntry extends BaseEntity {

    private boolean hasFinished;
    private double finishTime;
    @ManyToOne
    private Car car;
    @ManyToOne
    private Racer racer;
    @ManyToOne
    private Race race;

}
