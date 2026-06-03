package com.moviebooking.service;

import com.moviebooking.dto.AuthRequest;
import com.moviebooking.dto.AuthResponse;
import com.moviebooking.model.User;
import com.moviebooking.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse signup(AuthRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            return new AuthResponse(false, "Email already registered. Please login.", null, null);
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return new AuthResponse(true, "Account created successfully!", user.getName(), user.getEmail());
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmailIgnoreCase(request.getEmail().trim())
                .orElse(null);
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            return new AuthResponse(false, "Invalid email or password.", null, null);
        }
        return new AuthResponse(true, "Login successful!", user.getName(), user.getEmail());
    }
}
