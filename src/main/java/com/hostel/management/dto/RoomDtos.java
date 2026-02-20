package com.hostel.management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class RoomDtos {

    public record HostelRequest(@NotBlank String name, @NotBlank String address) {}

    public record FloorRequest(@NotNull UUID hostelId, @Min(0) int floorNumber) {}

    public record RoomRequest(
            @NotNull UUID floorId,
            @NotBlank String roomNumber,
            @Min(1) int capacity,
            @NotNull BigDecimal monthlyRent
    ) {}

    public record BedRequest(@NotNull UUID roomId, @NotBlank String bedNumber) {}

    public record RoomResponse(
            UUID roomId,
            String hostelName,
            int floorNumber,
            String roomNumber,
            int capacity,
            long availableBeds,
            BigDecimal monthlyRent
    ) {}
}
