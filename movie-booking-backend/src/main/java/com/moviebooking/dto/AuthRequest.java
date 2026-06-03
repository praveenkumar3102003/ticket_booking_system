package com.moviebooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for signup and login")
public class AuthRequest {

    @Schema(description = "Full name of the user (required for signup only)", example = "Praveen Kumar")
    private String name;

    @Schema(description = "Email address of the user", example = "praveen@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Password for the account", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
