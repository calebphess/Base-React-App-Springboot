package com.zetech.thingapp.thingapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration

// TODO: make api version, production url, and tos url all env variables from .env
// TODO: add real email and license and tos
@OpenAPIDefinition(
    info = @Info(
        title = "Thing App API", 
        version = "1", 
        contact = @Contact(name = "Penn Hess", email = "pennhess@thingapp.com", url = "https://github.com/calebphess"),
        license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
        termsOfService = "https://www.thingapp.com/tos",
        description = "Thing App REST API"
    ),
    servers = {
        // thingapp is required here for dev or the swagger docs won't call the proper API URLs
        @Server(url = "http://localhost:8080/thingapp", description = "Development"),
        @Server(url = "https://www.thingapp.com", description = "Production")
    }
)

public class SwaggerConfiguration 
{
    @Bean
    public OpenAPI customizeOpenAPI() 
    {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI().addSecurityItem(
            new SecurityRequirement().addList(securitySchemeName)).components(
                new Components().addSecuritySchemes(securitySchemeName,
                    new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                )
            );
    }
}