package com.deliverytech.delivery.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Delivery Tech API")
                        .version("1.0.0")
                        .description("API REST para sistema de delivery de comida.")
                        .contact(new Contact()
                                .name("Richard Lopes")
                                .url("https://github.com/rickmasterbr")
                                .email("contato@deliverytech.com")));
    }
}