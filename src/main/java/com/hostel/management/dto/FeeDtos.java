package com.hostel.management.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class FeeDtos {

    public record InvoiceCreateRequest(@NotNull UUID studentId, @NotNull LocalDate billingMonth, @NotNull BigDecimal totalAmount) {}

    public record PaymentRequest(@NotNull UUID invoiceId, @NotNull BigDecimal amount) {}

    public record InvoiceResponse(UUID invoiceId, UUID studentId, BigDecimal totalAmount, BigDecimal paidAmount, String status) {}
}
