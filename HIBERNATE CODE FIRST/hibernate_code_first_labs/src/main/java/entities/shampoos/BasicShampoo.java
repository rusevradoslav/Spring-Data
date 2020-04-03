package entities.shampoos;

import entities.ingredients.BasicIngredient;
import entities.labels.BasicLabel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shampoo")
public class BasicShampoo implements Shampoo {

    @Id
    @GeneratedValue
    private long id;
    private BigDecimal price;
    private String brand;

    @Enumerated(EnumType.STRING)
    private Size size;

    @OneToOne(cascade = CascadeType.ALL)
    private BasicLabel label;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<BasicIngredient> ingredients;

    protected BasicShampoo() {
        this.ingredients = new HashSet<>();
    }

    public BasicShampoo(String brand, BigDecimal price, Size size, BasicLabel label) {
        this();

        this.price = price;
        this.brand = brand;
        this.size = size;
        this.label = label;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Set<BasicIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<BasicIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
