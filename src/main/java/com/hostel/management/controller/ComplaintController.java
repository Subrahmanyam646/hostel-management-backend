package com.hostel.management.controller;

import com.hostel.management.dto.ComplaintDtos;
import com.hostel.management.service.ComplaintService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping
    public ResponseEntity<ComplaintDtos.ComplaintResponse> raise(@Valid @RequestBody ComplaintDtos.ComplaintCreateRequest request) {
        return ResponseEntity.ok(complaintService.raise(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','WARDEN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ComplaintDtos.ComplaintResponse> updateStatus(@PathVariable UUID id,
                                                                        @Valid @RequestBody ComplaintDtos.ComplaintStatusUpdateRequest request) {
        return ResponseEntity.ok(complaintService.updateStatus(id, request));
    }

    @GetMapping
    public ResponseEntity<Page<ComplaintDtos.ComplaintResponse>> list(@RequestParam UUID studentId, Pageable pageable) {
        return ResponseEntity.ok(complaintService.list(studentId, pageable));
    }
}
