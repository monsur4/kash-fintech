package com.kash.config;

import com.kash.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

@Configuration
public class KashConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
