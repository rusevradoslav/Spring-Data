package com.example.game_store.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
@Data
@NoArgsConstructor
@Entity
@Table(name = "games")
public class Game extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String trailer;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private double size;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate release_date;


    @ManyToMany(mappedBy = "games")
    private Set<Order> orders;

    @ManyToMany(mappedBy = "games")
    private Set<User> users;
}
