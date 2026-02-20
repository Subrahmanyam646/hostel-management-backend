package com.hostel.management.service.impl;

import com.hostel.management.dto.ComplaintDtos;
import com.hostel.management.entity.Complaint;
import com.hostel.management.entity.enums.ComplaintStatus;
import com.hostel.management.exception.ResourceNotFoundException;
import com.hostel.management.repository.ComplaintRepository;
import com.hostel.management.repository.StudentRepository;
import com.hostel.management.service.ComplaintService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final StudentRepository studentRepository;

    @Override
    public ComplaintDtos.ComplaintResponse raise(ComplaintDtos.ComplaintCreateRequest request) {
        var student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Complaint complaint = new Complaint();
        complaint.setStudent(student);
        complaint.setMessage(request.message());
        complaint.setStatus(ComplaintStatus.OPEN);
        return map(complaintRepository.save(complaint));
    }

    @Override
    public ComplaintDtos.ComplaintResponse updateStatus(UUID complaintId, ComplaintDtos.ComplaintStatusUpdateRequest request) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));
        complaint.setStatus(request.status());
        return map(complaintRepository.save(complaint));
    }

    @Override
    public Page<ComplaintDtos.ComplaintResponse> list(UUID studentId, Pageable pageable) {
        return complaintRepository.findByStudentId(studentId, pageable).map(this::map);
    }

    private ComplaintDtos.ComplaintResponse map(Complaint complaint) {
        return new ComplaintDtos.ComplaintResponse(
                complaint.getId(),
                complaint.getStudent().getId(),
                complaint.getMessage(),
                complaint.getStatus()
        );
    }
}
