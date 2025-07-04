package com.cbcf.namf.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CBCF Namf Communication Service API")
                        .version("1.0.0")
                        .description("5G SA Cell Broadcast Center Function Namf_Communication Service Implementation")
                        .contact(new Contact()
                                .name("5G Development Team")
                                .email("dev@5g-cbcf.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
