package com.hostel.management.controller;

import com.hostel.management.dto.FeeDtos;
import com.hostel.management.service.FeeService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fees")
@RequiredArgsConstructor
public class FeeController {

    private final FeeService feeService;

    @PreAuthorize("hasAnyRole('ADMIN','WARDEN')")
    @PostMapping("/invoices")
    public ResponseEntity<FeeDtos.InvoiceResponse> createInvoice(@Valid @RequestBody FeeDtos.InvoiceCreateRequest request) {
        return ResponseEntity.ok(feeService.createInvoice(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','WARDEN')")
    @PostMapping("/payments")
    public ResponseEntity<FeeDtos.InvoiceResponse> pay(@Valid @RequestBody FeeDtos.PaymentRequest request) {
        return ResponseEntity.ok(feeService.recordPayment(request));
    }

    @GetMapping("/students/{studentId}/invoices")
    public ResponseEntity<List<FeeDtos.InvoiceResponse>> studentInvoices(@PathVariable UUID studentId) {
        return ResponseEntity.ok(feeService.studentInvoices(studentId));
    }
}
