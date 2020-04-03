package com.json_exercises.product_shop.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private int age;
    private String firstName;
    private String lastName;
    private Set<User> friends;
    private Set<Product> productBuys;
    private Set<Product> productSells;

    public User() {
    }

    @Column
    public int getAge() {
        return age;
    }

    @Column
    public String getFirstName() {
        return firstName;
    }

    @Column(nullable = false)
    @Length(min = 3, message = "Last name must be at least 3 symbols!")
    public String getLastName() {
        return lastName;
    }

    @ManyToMany
    @JoinTable(name = "users_friends",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    public Set<User> getFriends() {
        return friends;
    }

    @OneToMany(mappedBy = "buyer",targetEntity = Product.class,fetch = FetchType.EAGER)
    public Set<Product> getProductBuys() {
        return productBuys;
    }

    @OneToMany(mappedBy = "seller",targetEntity = Product.class,fetch = FetchType.EAGER)
    public Set<Product> getProductSells() {
        return productSells;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public void setProductBuys(Set<Product> productBuys) {
        this.productBuys = productBuys;
    }

    public void setProductSells(Set<Product> productSells) {
        this.productSells = productSells;
    }
}
