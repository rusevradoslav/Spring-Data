package entities.university_system_03;

import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name = "studets")
public class Student extends BaseEntity03 {

    private double average_grade;
    private long attendance;
    private Set<Course> courses;


    public Student() {
    }

    @Column(name = "average_grade")
    public double getAverage_grade() {
        return average_grade;
    }

    public void setAverage_grade(double average_grade) {
        this.average_grade = average_grade;
    }
    @Column(name = "attendance")
    public long getAttendance() {
        return attendance;
    }

    public void setAttendance(long attendance) {
        this.attendance = attendance;
    }
    @ManyToMany(targetEntity = Course.class)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
