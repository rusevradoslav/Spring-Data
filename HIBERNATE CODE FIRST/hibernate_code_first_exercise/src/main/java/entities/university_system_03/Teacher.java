package entities.university_system_03;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends BaseEntity03 {


    private String email;
    private BigDecimal salary_per_hour;
    private Set<Course> courses;

    public Teacher() {
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "salary_per_hour")
    public BigDecimal getSalary_per_hour() {
        return salary_per_hour;
    }

    public void setSalary_per_hour(BigDecimal salary_per_hour) {
        this.salary_per_hour = salary_per_hour;
    }

    @Column(name = "courses")
    @OneToMany
    @JoinColumn(name = "course_id",referencedColumnName = "id")
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
