package com.mdotm.petapi.pet.common.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myOpenAPI() {

        Info info = new Info()
                .title("Pets Management API")
                .version("1.0")
                .description("This API exposes endpoints to manage Pets.");

        return new OpenAPI().info(info);
    }
}