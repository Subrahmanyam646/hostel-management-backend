package com.hostel.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class StudentDtos {

    public record StudentUpsertRequest(
            @NotNull UUID userId,
            @NotBlank String phone,
            @NotBlank String guardianName,
            @NotBlank String guardianPhone,
            @NotBlank String enrollmentNumber
    ) {}

    public record StudentResponse(
            UUID id,
            UUID userId,
            String fullName,
            String email,
            String phone,
            String guardianName,
            String guardianPhone,
            String enrollmentNumber
    ) {}
}
