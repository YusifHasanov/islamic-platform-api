package com.example.api.core.config;

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
}
