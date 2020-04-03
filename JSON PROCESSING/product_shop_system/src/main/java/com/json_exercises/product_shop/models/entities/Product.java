package com.json_exercises.product_shop.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;
    private BigDecimal price;
    private User buyer;
    private User seller;
    private Set<Category> categories;

    public Product() {
    }

    @Column(nullable = false)
    @Length(min = 3, message = "Name must be at least 3 symbols!")
    public String getName() {
        return name;
    }

    @Column
    public BigDecimal getPrice() {
        return price;
    }

    @ManyToOne
    public User getBuyer() {
        return buyer;
    }

    @ManyToOne(optional = false)
    @NotNull(message = "Buyer Id can not be null.")
    public User getSeller() {
        return seller;
    }

    @ManyToMany
    public Set<Category> getCategories() {
        return categories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
