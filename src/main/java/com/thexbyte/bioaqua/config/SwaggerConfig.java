package com.thexbyte.bioaqua.config;

 

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; 

 
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bioaqua  API")
                        .version("1.0")
                        .description("API for bioaqua solutions")
                        .contact(new Contact()
                                .name("admin")
                                .email("contact@bioaqua-solutions.tn")
                                .url("https://bioaqua-solutions.tn")));
    }



}