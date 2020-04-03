package com.example.game_store.services.impl;

import com.example.game_store.domain.dtos.user_dtos.UserDto;
import com.example.game_store.domain.dtos.user_dtos.UserLogInDto;
import com.example.game_store.domain.dtos.user_dtos.UserRegisterDto;
import com.example.game_store.domain.entities.User;
import com.example.game_store.domain.enums.Role;
import com.example.game_store.repositories.UserRepository;
import com.example.game_store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private UserDto userDto;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;

    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        User user = this.modelMapper.map(userRegisterDto, User.class);

        user.setRole(this.userRepository.count() == 0 ? Role.ADMIN : Role.USER);

        this.userRepository.saveAndFlush(user);

    }

    @Override
    public User loginUser(UserLogInDto userLogInDto) {
        User user = this.userRepository.findByEmail(userLogInDto.getEmail());

        if (user == null) {
            System.out.println("Incorrect username / password");
        } else {
            this.userDto = this.modelMapper.map(user, UserDto.class);
            System.out.printf("Successfully logged in %s%n", userDto.getFullName());
        }
        return user;
    }

    @Override
    public void logOut() {

        if (this.userDto == null) {
            System.out.println("Cannot log out. No user was logged in.");
        } else {
            String name = this.userDto.getFullName();
            this.userDto = null;
            System.out.printf("User %s successfully logged out%n",name);
        }
    }


}
