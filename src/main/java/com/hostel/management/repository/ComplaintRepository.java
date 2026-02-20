package com.hostel.management.repository;

import com.hostel.management.entity.Complaint;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, UUID> {
    Page<Complaint> findByStudentId(UUID studentId, Pageable pageable);
}
