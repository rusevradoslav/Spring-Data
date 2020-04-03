package com.example.game_store.services;

import com.example.game_store.domain.dtos.user_dtos.UserLogInDto;
import com.example.game_store.domain.dtos.user_dtos.UserRegisterDto;
import com.example.game_store.domain.entities.User;

public interface UserService {

    void registerUser(UserRegisterDto userRegisterDto);

    User loginUser(UserLogInDto userLogInDto);

    void logOut();

}
