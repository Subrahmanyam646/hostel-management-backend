package com.hostel.management.repository;

import com.hostel.management.entity.Floor;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FloorRepository extends JpaRepository<Floor, UUID> {
}
