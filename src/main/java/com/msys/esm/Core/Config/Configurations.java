package com.msys.esm.Core.Config;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;


@Configuration
@Lazy
public class Configurations {
    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }
    @Bean
    public Validator validator() {
     return  Validation.buildDefaultValidatorFactory().getValidator();
    }

}
