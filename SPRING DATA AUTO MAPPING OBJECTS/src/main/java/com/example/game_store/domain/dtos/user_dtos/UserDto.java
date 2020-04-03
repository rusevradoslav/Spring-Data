package com.example.game_store.domain.dtos.user_dtos;

import com.example.game_store.domain.entities.Game;
import com.example.game_store.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    private String email;
    private String password;
    private String fullName;
    private Set<Game> games;
    private Role role;

}
