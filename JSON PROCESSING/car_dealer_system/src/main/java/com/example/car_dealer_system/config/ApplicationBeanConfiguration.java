package com.example.car_dealer_system.config;


import com.example.car_dealer_system.adapters.jsonAdapter;
import com.example.car_dealer_system.adapters.jsonAdapter2;
import com.example.car_dealer_system.utils.FileUtil;
import com.example.car_dealer_system.utils.ValidationUtil;
import com.example.car_dealer_system.utils.utilImpl.FileUtilImpl;
import com.example.car_dealer_system.utils.utilImpl.ValidationUtilImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Random;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BufferedReader bufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public FileUtil fileUtil() {
        return new FileUtilImpl();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new jsonAdapter())
                .registerTypeAdapter(LocalDateTime.class,new jsonAdapter2())
                .create();
    }

    @Bean
    public Random random(){
        return new Random();
    }
}
