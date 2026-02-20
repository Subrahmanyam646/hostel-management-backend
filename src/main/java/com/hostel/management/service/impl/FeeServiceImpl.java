package com.hostel.management.service.impl;

import com.hostel.management.dto.FeeDtos;
import com.hostel.management.entity.Invoice;
import com.hostel.management.entity.enums.InvoiceStatus;
import com.hostel.management.exception.BadRequestException;
import com.hostel.management.exception.ResourceNotFoundException;
import com.hostel.management.repository.InvoiceRepository;
import com.hostel.management.repository.StudentRepository;
import com.hostel.management.service.FeeService;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeeServiceImpl implements FeeService {

    private final InvoiceRepository invoiceRepository;
    private final StudentRepository studentRepository;

    @Override
    public FeeDtos.InvoiceResponse createInvoice(FeeDtos.InvoiceCreateRequest request) {
        var student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Invoice invoice = new Invoice();
        invoice.setStudent(student);
        invoice.setBillingMonth(request.billingMonth());
        invoice.setTotalAmount(request.totalAmount());
        invoice.setStatus(InvoiceStatus.PENDING);
        return map(invoiceRepository.save(invoice));
    }

    @Override
    public FeeDtos.InvoiceResponse recordPayment(FeeDtos.PaymentRequest request) {
        Invoice invoice = invoiceRepository.findById(request.invoiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
        BigDecimal updatedPaid = invoice.getPaidAmount().add(request.amount());
        if (updatedPaid.compareTo(invoice.getTotalAmount()) > 0) {
            throw new BadRequestException("Payment exceeds total amount");
        }
        invoice.setPaidAmount(updatedPaid);
        if (updatedPaid.compareTo(invoice.getTotalAmount()) == 0) {
            invoice.setStatus(InvoiceStatus.PAID);
        } else {
            invoice.setStatus(InvoiceStatus.PARTIAL);
        }
        return map(invoiceRepository.save(invoice));
    }

    @Override
    public List<FeeDtos.InvoiceResponse> studentInvoices(UUID studentId) {
        return invoiceRepository.findByStudentId(studentId).stream().map(this::map).toList();
    }

    private FeeDtos.InvoiceResponse map(Invoice invoice) {
        return new FeeDtos.InvoiceResponse(
                invoice.getId(),
                invoice.getStudent().getId(),
                invoice.getTotalAmount(),
                invoice.getPaidAmount(),
                invoice.getStatus().name()
        );
    }
}
