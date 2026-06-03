package com.moviebooking.controller;

import com.moviebooking.dto.AuthRequest;
import com.moviebooking.dto.AuthResponse;
import com.moviebooking.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "User signup and login endpoints")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
        summary = "Register a new user",
        description = "Creates a new user account. Returns error if email is already registered."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Signup successful or email already exists",
            content = @Content(schema = @Schema(implementation = AuthResponse.class),
                examples = {
                    @ExampleObject(name = "Success", value = """
                        {
                          "success": true,
                          "message": "Account created successfully!",
                          "name": "Praveen Kumar",
                          "email": "praveen@example.com"
                        }"""),
                    @ExampleObject(name = "Duplicate Email", value = """
                        {
                          "success": false,
                          "message": "Email already registered. Please login.",
                          "name": null,
                          "email": null
                        }""")
                }))
    })
    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody AuthRequest request) {
        return userService.signup(request);
    }

    @Operation(
        summary = "Login with existing account",
        description = "Validates email and password. Returns user details on success."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login successful or invalid credentials",
            content = @Content(schema = @Schema(implementation = AuthResponse.class),
                examples = {
                    @ExampleObject(name = "Success", value = """
                        {
                          "success": true,
                          "message": "Login successful!",
                          "name": "Praveen Kumar",
                          "email": "praveen@example.com"
                        }"""),
                    @ExampleObject(name = "Invalid Credentials", value = """
                        {
                          "success": false,
                          "message": "Invalid email or password.",
                          "name": null,
                          "email": null
                        }""")
                }))
    })
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return userService.login(request);
    }
}
