package com.neeraj.auth.authservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
            .info(new Info()
                .title("Auth Service API")
                .version("1.0")
                .description("""
                    Production-grade Authentication Service
                    
                    Features:
                    - JWT Authentication
                    - Refresh Token Rotation
                    - Email Verification
                    - Account Locking
                    - Rate Limiting
                    - Secure Logout
                """)
            )
            .addSecurityItem(
                new SecurityRequirement().addList(SECURITY_SCHEME_NAME)
            )
            .components(
                new Components()
                    .addSecuritySchemes(
                        SECURITY_SCHEME_NAME,
                        new SecurityScheme()
                            .name("Authorization")
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            );
    }
}
