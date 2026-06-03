package com.moviebooking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI movieBookingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MovieBook — Movie Ticket Booking API")
                        .description(
                            "REST API for the MovieBook application. " +
                            "Provides endpoints for user authentication, " +
                            "browsing movies, creating bookings, and viewing booking history."
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("MovieBook Support")
                                .email("support@moviebook.com"))
                        .license(new License()
                                .name("MIT License")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development Server")
                ));
    }
}
