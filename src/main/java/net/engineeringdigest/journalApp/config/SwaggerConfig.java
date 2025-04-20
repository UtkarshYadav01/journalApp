package net.engineeringdigest.journalApp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomConfig() {
        return new OpenAPI()
                .info(
                        new Info().title("Journal App APIs")
                                .description("By Utkarsh")
                )
                .servers(Arrays.asList(new Server().url("http://localhost:8080").description("local"),
                        new Server().url("https://journalapp-render.onrender.com").description("live")))
                .tags(Arrays.asList(
                        new Tag().name("Public Access").description("Open endpoints, no login required \uD83D\uDD13"),
                        new Tag().name("Health Check").description("Service status and health check ✅"),
                        new Tag().name("Authentication").description("Login and token management \uD83D\uDD11"),
                        new Tag().name("Authorized Access").description("Protected endpoints, login required \uD83D\uDD12"),
                        new Tag().name("Journal APIs").description("Manage your journal entries \uD83D\uDCD6"),
                        new Tag().name("User APIs").description("User profile and settings 👤"),
                        new Tag().name("Admin APIs").description("Admin controls and user management ⚙️")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes(
                        "bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                ));
    }
}