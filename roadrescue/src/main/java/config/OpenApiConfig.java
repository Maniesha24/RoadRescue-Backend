package com.roadrescue.roadrescue.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI roadRescueAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Road Rescue API")
                                .version("1.0")
                                .description(
                                        "Roadside Assistance Platform APIs"
                                )
                );
    }
}