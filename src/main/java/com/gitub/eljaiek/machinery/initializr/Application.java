package com.gitub.eljaiek.machinery.initializr;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    StartInitializrMetadataUpdateStrategy initializrMetadataUpdateStrategy(
            RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        return new StartInitializrMetadataUpdateStrategy(restTemplateBuilder.build(), objectMapper);
    }
}
