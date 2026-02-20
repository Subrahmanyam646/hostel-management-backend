package com.hostel.management.controller;

import com.hostel.management.dto.RoomDtos;
import com.hostel.management.service.HostelService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hostel")
@RequiredArgsConstructor
public class HostelController {

    private final HostelService hostelService;

    @PreAuthorize("hasAnyRole('ADMIN','WARDEN')")
    @PostMapping
    public ResponseEntity<UUID> createHostel(@Valid @RequestBody RoomDtos.HostelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hostelService.createHostel(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','WARDEN')")
    @PostMapping("/floors")
    public ResponseEntity<UUID> createFloor(@Valid @RequestBody RoomDtos.FloorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hostelService.createFloor(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','WARDEN')")
    @PostMapping("/rooms")
    public ResponseEntity<UUID> createRoom(@Valid @RequestBody RoomDtos.RoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hostelService.createRoom(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','WARDEN')")
    @PostMapping("/beds")
    public ResponseEntity<UUID> createBed(@Valid @RequestBody RoomDtos.BedRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hostelService.createBed(request));
    }

    @GetMapping("/rooms/search")
    public ResponseEntity<Page<RoomDtos.RoomResponse>> searchRooms(
            @RequestParam(required = false) UUID hostelId,
            @RequestParam(required = false) Integer floorNumber,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) BigDecimal maxRent,
            Pageable pageable) {
        return ResponseEntity.ok(hostelService.searchRooms(hostelId, floorNumber, minCapacity, maxRent, pageable));
    }
}
