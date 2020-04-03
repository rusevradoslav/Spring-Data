package com.example.car_product.config;



import com.example.car_product.utils.FileUtil;
import com.example.car_product.utils.ValidationUtil;
import com.example.car_product.utils.XmlParser;
import com.example.car_product.utils.utilImpl.FileUtilImpl;
import com.example.car_product.utils.utilImpl.ValidationUtilImpl;
import com.example.car_product.utils.utilImpl.XmlParserImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    public ValidationUtil validationUtil() { return new ValidationUtilImpl();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }
    @Bean
    public XmlParser xmlParser(){
        return new XmlParserImpl();
    }

    @Bean
    public FileUtil fileUtil() {
        return new FileUtilImpl();
    }

    @Bean
    public Random random(){
        return new Random();
    }
}
