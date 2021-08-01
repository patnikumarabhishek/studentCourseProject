package com.rootlets.registration.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanFactory {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
