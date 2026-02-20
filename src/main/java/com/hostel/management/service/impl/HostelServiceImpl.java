package com.hostel.management.service.impl;

import com.hostel.management.dto.RoomDtos;
import com.hostel.management.entity.Bed;
import com.hostel.management.entity.Floor;
import com.hostel.management.entity.Hostel;
import com.hostel.management.entity.Room;
import com.hostel.management.exception.ResourceNotFoundException;
import com.hostel.management.repository.BedRepository;
import com.hostel.management.repository.FloorRepository;
import com.hostel.management.repository.HostelRepository;
import com.hostel.management.repository.RoomRepository;
import com.hostel.management.service.HostelService;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HostelServiceImpl implements HostelService {

    private final HostelRepository hostelRepository;
    private final FloorRepository floorRepository;
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;

    @Override
    public UUID createHostel(RoomDtos.HostelRequest request) {
        Hostel hostel = new Hostel();
        hostel.setName(request.name());
        hostel.setAddress(request.address());
        return hostelRepository.save(hostel).getId();
    }

    @Override
    public UUID createFloor(RoomDtos.FloorRequest request) {
        Hostel hostel = hostelRepository.findById(request.hostelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hostel not found"));
        Floor floor = new Floor();
        floor.setFloorNumber(request.floorNumber());
        floor.setHostel(hostel);
        return floorRepository.save(floor).getId();
    }

    @Override
    public UUID createRoom(RoomDtos.RoomRequest request) {
        Floor floor = floorRepository.findById(request.floorId())
                .orElseThrow(() -> new ResourceNotFoundException("Floor not found"));
        Room room = new Room();
        room.setFloor(floor);
        room.setRoomNumber(request.roomNumber());
        room.setCapacity(request.capacity());
        room.setMonthlyRent(request.monthlyRent());
        return roomRepository.save(room).getId();
    }

    @Override
    public UUID createBed(RoomDtos.BedRequest request) {
        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        Bed bed = new Bed();
        bed.setRoom(room);
        bed.setBedNumber(request.bedNumber());
        bed.setOccupied(false);
        return bedRepository.save(bed).getId();
    }

    @Override
    public Page<RoomDtos.RoomResponse> searchRooms(UUID hostelId, Integer floorNumber, Integer minCapacity, BigDecimal maxRent, Pageable pageable) {
        return roomRepository.searchRooms(hostelId, floorNumber, minCapacity, maxRent, pageable)
                .map(room -> new RoomDtos.RoomResponse(
                        room.getId(),
                        room.getFloor().getHostel().getName(),
                        room.getFloor().getFloorNumber(),
                        room.getRoomNumber(),
                        room.getCapacity(),
                        bedRepository.countByRoomIdAndOccupiedFalse(room.getId()),
                        room.getMonthlyRent()
                ));
    }
}
