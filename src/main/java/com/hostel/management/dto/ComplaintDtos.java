package com.hostel.management.dto;

import com.hostel.management.entity.enums.ComplaintStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class ComplaintDtos {

    public record ComplaintCreateRequest(@NotNull UUID studentId, @NotBlank String message) {}

    public record ComplaintStatusUpdateRequest(@NotNull ComplaintStatus status) {}

    public record ComplaintResponse(UUID id, UUID studentId, String message, ComplaintStatus status) {}
}
