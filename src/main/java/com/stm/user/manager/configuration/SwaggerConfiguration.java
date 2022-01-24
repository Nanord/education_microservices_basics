package com.stm.user.manager.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Value("${spring.application.version}")
    private String version;

    @Bean
    //todo: Пример шапки описания сервиса в swagger
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("User Manager")
                        .description("Описание API")
                        .version(version))
                .externalDocs(new ExternalDocumentation()
                        .description("Сервис самых базовых основ.")
                        .url("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
    }
}
