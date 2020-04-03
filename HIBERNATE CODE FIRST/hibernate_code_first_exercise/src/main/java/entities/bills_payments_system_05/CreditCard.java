package entities.bills_payments_system_05;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "credit_card")
public class CreditCard extends BillingDetail {

    private String card_type;
    private Date expiration_month;
    private Date expiration_yea;
    private User owner;

    public CreditCard() {
    }

    @Column(name = "card_type")
    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    @Column(name = "expiration_month")
    public Date getExpiration_month() {
        return expiration_month;
    }

    public void setExpiration_month(Date expiration_month) {
        this.expiration_month = expiration_month;
    }

    @Column(name = "expiration_yea")
    public Date getExpiration_yea() {
        return expiration_yea;
    }

    public void setExpiration_yea(Date expiration_yea) {
        this.expiration_yea = expiration_yea;
    }

}
