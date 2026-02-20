package com.hostel.management.repository;

import com.hostel.management.entity.Allocation;
import com.hostel.management.entity.enums.AllocationStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllocationRepository extends JpaRepository<Allocation, UUID> {
    Optional<Allocation> findByStudentIdAndStatus(UUID studentId, AllocationStatus status);
    List<Allocation> findByStudentIdOrderByCreatedAtDesc(UUID studentId);
}
