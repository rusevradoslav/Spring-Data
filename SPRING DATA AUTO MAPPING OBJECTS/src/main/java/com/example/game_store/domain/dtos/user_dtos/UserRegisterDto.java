package com.example.game_store.domain.dtos.user_dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {


    @NotNull
    @Pattern(regexp = "^\\w+@[a-z_]+?\\.[a-zA-Z]{2,3}$" , message = "Email is not valid.")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$" , message = "Password is not valid.")
    @Size(min = 6 , message = "Password must be minimum 6 characters")
    private String password;

    @NotNull(message = "Full name must not be null.")
    private String fullName;


}
