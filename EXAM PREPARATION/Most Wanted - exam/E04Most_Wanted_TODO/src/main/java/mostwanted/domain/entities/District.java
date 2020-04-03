package mostwanted.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "districts")
public class District extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne
    private Town town;




}
