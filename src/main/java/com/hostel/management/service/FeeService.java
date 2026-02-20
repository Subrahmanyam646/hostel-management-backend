package com.hostel.management.service;

import com.hostel.management.dto.FeeDtos;
import java.util.List;
import java.util.UUID;

public interface FeeService {
    FeeDtos.InvoiceResponse createInvoice(FeeDtos.InvoiceCreateRequest request);
    FeeDtos.InvoiceResponse recordPayment(FeeDtos.PaymentRequest request);
    List<FeeDtos.InvoiceResponse> studentInvoices(UUID studentId);
}
