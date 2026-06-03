package com.moviebooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response body for signup and login operations")
public class AuthResponse {

    @Schema(description = "Whether the operation succeeded", example = "true")
    private boolean success;

    @Schema(description = "Message describing the result", example = "Login successful!")
    private String message;

    @Schema(description = "Name of the user (null on failure)", example = "Praveen Kumar")
    private String name;

    @Schema(description = "Email of the user (null on failure)", example = "praveen@example.com")
    private String email;

    public AuthResponse(boolean success, String message, String name, String email) {
        this.success = success;
        this.message = message;
        this.name = name;
        this.email = email;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
