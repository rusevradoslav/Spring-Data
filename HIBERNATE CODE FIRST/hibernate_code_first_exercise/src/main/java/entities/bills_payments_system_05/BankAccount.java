package entities.bills_payments_system_05;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount extends BillingDetail {
    private String bank_name;
    private String swift;

    public BankAccount() {
    }

    @Column(name = "bank_name")
    public String getName() {
        return bank_name;
    }

    public void setName(String bank_name) {
        this.bank_name = bank_name;
    }

    @Column(name = "swift")
    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }
}
