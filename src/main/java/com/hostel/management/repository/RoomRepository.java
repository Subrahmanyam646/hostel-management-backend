package com.hostel.management.repository;

import com.hostel.management.entity.Room;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, UUID> {

    @Query("""
            select r from Room r
            where (:hostelId is null or r.floor.hostel.id = :hostelId)
              and (:floorNumber is null or r.floor.floorNumber = :floorNumber)
              and (:minCapacity is null or r.capacity >= :minCapacity)
              and (:maxRent is null or r.monthlyRent <= :maxRent)
            """)
    Page<Room> searchRooms(@Param("hostelId") UUID hostelId,
                           @Param("floorNumber") Integer floorNumber,
                           @Param("minCapacity") Integer minCapacity,
                           @Param("maxRent") java.math.BigDecimal maxRent,
                           Pageable pageable);
}
