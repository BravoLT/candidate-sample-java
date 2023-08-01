package com.bravo.user.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI microserviceOpenAPI() {
        return new OpenAPI().info(new Info().title("Candidate Java Sample")
                .description("Candidate Java API")
                .version("1.0"));

    }
}
