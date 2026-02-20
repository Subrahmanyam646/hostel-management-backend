package com.hostel.management.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public class AllocationDtos {

    public record AllocateRequest(@NotNull UUID studentId, @NotNull UUID bedId, @NotNull LocalDate fromDate) {}

    public record TransferRequest(@NotNull UUID studentId, @NotNull UUID newBedId, @NotNull LocalDate transferDate) {}

    public record AllocationResponse(UUID id, UUID studentId, UUID bedId, String status, LocalDate fromDate, LocalDate toDate) {}
}
