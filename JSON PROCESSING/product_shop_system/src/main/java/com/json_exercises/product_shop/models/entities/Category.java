package com.json_exercises.product_shop.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    String name;
    Set<Product> products;

    public Category() {
    }

    @Column(nullable = false)
    @Length(min = 3, max = 15, message = "Name must be between 3 and 15 symbols!")
    public String getName() {
        return name;
    }

    @ManyToMany(mappedBy = "categories", targetEntity = Product.class, fetch = FetchType.EAGER)
    public Set<Product> getProducts() {
        return products;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
