package entities.bills_payments_system_05;

import entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "billing_details")
public class BillingDetail extends BaseEntity {
    private String card_number;
    private User owner;

    public BillingDetail() {
    }

    @Column(name = "card_number")
    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
