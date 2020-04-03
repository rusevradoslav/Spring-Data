package entities.bills_payments_system_05;

import entities.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private Set<BillingDetail> billing_detail;

    public User() {
    }

    @Column(name = "first_name")
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @Column(name = "last_name")
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "owner")
    public Set<BillingDetail> getBilling_detail() {
        return billing_detail;
    }

    public void setBilling_detail(Set<BillingDetail> billing_detail) {
        this.billing_detail = billing_detail;
    }
}
