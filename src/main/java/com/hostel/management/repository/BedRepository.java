package com.hostel.management.repository;

import com.hostel.management.entity.Bed;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BedRepository extends JpaRepository<Bed, UUID> {
    long countByRoomIdAndOccupiedFalse(UUID roomId);
    Optional<Bed> findFirstByRoomIdAndOccupiedFalse(UUID roomId);
}
