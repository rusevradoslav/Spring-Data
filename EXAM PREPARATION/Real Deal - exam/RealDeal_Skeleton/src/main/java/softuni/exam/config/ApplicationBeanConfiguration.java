package softuni.exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.exam.adapters.JsonLocalDateAdapter;
import softuni.exam.adapters.JsonLocalDateTimeAdapter;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;
import softuni.exam.util.impl.ValidationUtilImpl;
import softuni.exam.util.impl.XmlParserImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Configuration
public class ApplicationBeanConfiguration {


    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new JsonLocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new JsonLocalDateTimeAdapter())
                .create();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }

}
