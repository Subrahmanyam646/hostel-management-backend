package com.hostel.management.controller;

import com.hostel.management.dto.AllocationDtos;
import com.hostel.management.service.AllocationService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/allocations")
@RequiredArgsConstructor
public class AllocationController {

    private final AllocationService allocationService;

    @PreAuthorize("hasAnyRole('ADMIN','WARDEN')")
    @PostMapping
    public ResponseEntity<AllocationDtos.AllocationResponse> allocate(@Valid @RequestBody AllocationDtos.AllocateRequest request) {
        return ResponseEntity.ok(allocationService.allocate(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','WARDEN')")
    @PostMapping("/transfer")
    public ResponseEntity<AllocationDtos.AllocationResponse> transfer(@Valid @RequestBody AllocationDtos.TransferRequest request) {
        return ResponseEntity.ok(allocationService.transfer(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','WARDEN')")
    @PostMapping("/{studentId}/vacate")
    public ResponseEntity<AllocationDtos.AllocationResponse> vacate(@PathVariable UUID studentId) {
        return ResponseEntity.ok(allocationService.vacate(studentId));
    }

    @GetMapping("/{studentId}/history")
    public ResponseEntity<List<AllocationDtos.AllocationResponse>> history(@PathVariable UUID studentId) {
        return ResponseEntity.ok(allocationService.history(studentId));
    }
}
