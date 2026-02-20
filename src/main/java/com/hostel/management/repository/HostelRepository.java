package com.hostel.management.repository;

import com.hostel.management.entity.Hostel;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostelRepository extends JpaRepository<Hostel, UUID> {
}
