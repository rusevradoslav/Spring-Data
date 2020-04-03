package com.json_exercises.product_shop.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.json_exercises.product_shop.utils.FileUtil;
import com.json_exercises.product_shop.utils.ValidationUtil;
import com.json_exercises.product_shop.utils.impl.FileUtilImpl;
import com.json_exercises.product_shop.utils.impl.ValidationUtilImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public FileUtil fileUtil() {
        return new FileUtilImpl();
    }
}
