package entities.hospital_database_04;

import javax.persistence.*;

@Entity
@Table(name = "medicaments")
public class Medicament {
    private int id;
    private String name;
    private Patient patient;

    public Medicament() {

    }

    public Medicament(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
