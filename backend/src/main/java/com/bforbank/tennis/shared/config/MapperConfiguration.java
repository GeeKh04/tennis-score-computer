package com.bforbank.tennis.shared.config;

import com.bforbank.tennis.service.GameMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public GameMapper gameMapper() {
        return GameMapper.INSTANCE;
    }
}
