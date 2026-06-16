package com.tarefasAPI.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI().info(new Info()
                .title("API de Gerenciamento de Tarefas")
                .version("1.0")
                .description("API para criação, atualização, conclusão e reabertura de tarefas"));
    }
}
