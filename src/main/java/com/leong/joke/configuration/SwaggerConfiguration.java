package com.leong.joke.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("Joke API documentation")
                .description("Documentation Joke API")
                .version("v1")
                .contact(getContactDetails()));
    }

    private Contact getContactDetails() {
        return new Contact().name("Contact Person").email("roberto@gmail.com").url("https://github.com/robertoleong");
    }
}