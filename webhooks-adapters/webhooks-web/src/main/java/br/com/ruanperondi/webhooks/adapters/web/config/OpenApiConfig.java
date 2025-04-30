package br.com.ruanperondi.webhooks.adapters.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Webhooks API")
                        .version("1.0.0")
                        .description("API para gerenciamento de webhooks")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
} 