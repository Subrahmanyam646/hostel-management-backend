package com.hostel.management.service;

import com.hostel.management.dto.ComplaintDtos;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ComplaintService {
    ComplaintDtos.ComplaintResponse raise(ComplaintDtos.ComplaintCreateRequest request);
    ComplaintDtos.ComplaintResponse updateStatus(UUID complaintId, ComplaintDtos.ComplaintStatusUpdateRequest request);
    Page<ComplaintDtos.ComplaintResponse> list(UUID studentId, Pageable pageable);
}
