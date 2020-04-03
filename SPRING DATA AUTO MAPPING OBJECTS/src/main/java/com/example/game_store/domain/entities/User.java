package com.example.game_store.domain.entities;


import com.example.game_store.domain.enums.Role;
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
@Table(name = "users")
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;


    @ManyToMany(cascade = {CascadeType.PERSIST
            , CascadeType.MERGE,
            CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_games",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "game_id",
                    referencedColumnName = "id"))
    private Set<Game> games;

    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Order> orders;
}
