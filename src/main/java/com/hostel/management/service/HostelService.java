package com.hostel.management.service;

import com.hostel.management.dto.RoomDtos;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HostelService {
    UUID createHostel(RoomDtos.HostelRequest request);
    UUID createFloor(RoomDtos.FloorRequest request);
    UUID createRoom(RoomDtos.RoomRequest request);
    UUID createBed(RoomDtos.BedRequest request);
    Page<RoomDtos.RoomResponse> searchRooms(UUID hostelId, Integer floorNumber, Integer minCapacity, BigDecimal maxRent, Pageable pageable);
}
