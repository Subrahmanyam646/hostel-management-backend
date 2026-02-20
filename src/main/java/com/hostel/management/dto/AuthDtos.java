package com.hostel.management.dto;

import com.hostel.management.entity.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDtos {

    public record RegisterRequest(
            @NotBlank String fullName,
            @NotBlank @Email String email,
            @NotBlank String password,
            RoleType role
    ) {}

    public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) {}

    public record AuthResponse(String token, String email, RoleType role) {}
}
