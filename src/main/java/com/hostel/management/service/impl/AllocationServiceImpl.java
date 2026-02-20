package com.hostel.management.service.impl;

import com.hostel.management.dto.AllocationDtos;
import com.hostel.management.entity.Allocation;
import com.hostel.management.entity.Bed;
import com.hostel.management.entity.enums.AllocationStatus;
import com.hostel.management.exception.BadRequestException;
import com.hostel.management.exception.ResourceNotFoundException;
import com.hostel.management.repository.AllocationRepository;
import com.hostel.management.repository.BedRepository;
import com.hostel.management.repository.StudentRepository;
import com.hostel.management.service.AllocationService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AllocationServiceImpl implements AllocationService {

    private final AllocationRepository allocationRepository;
    private final StudentRepository studentRepository;
    private final BedRepository bedRepository;

    @Override
    @Transactional
    public AllocationDtos.AllocationResponse allocate(AllocationDtos.AllocateRequest request) {
        var student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Bed bed = getAvailableBed(request.bedId());
        bed.setOccupied(true);

        Allocation allocation = new Allocation();
        allocation.setStudent(student);
        allocation.setBed(bed);
        allocation.setAllocatedFrom(request.fromDate());
        allocation.setStatus(AllocationStatus.ACTIVE);
        return map(allocationRepository.save(allocation));
    }

    @Override
    @Transactional
    public AllocationDtos.AllocationResponse transfer(AllocationDtos.TransferRequest request) {
        Allocation active = allocationRepository.findByStudentIdAndStatus(request.studentId(), AllocationStatus.ACTIVE)
                .orElseThrow(() -> new BadRequestException("No active allocation found"));
        active.setStatus(AllocationStatus.TRANSFERRED);
        active.setAllocatedTo(request.transferDate());
        active.getBed().setOccupied(false);

        Bed newBed = getAvailableBed(request.newBedId());
        newBed.setOccupied(true);

        Allocation transferred = new Allocation();
        transferred.setStudent(active.getStudent());
        transferred.setBed(newBed);
        transferred.setAllocatedFrom(request.transferDate());
        transferred.setStatus(AllocationStatus.ACTIVE);
        return map(allocationRepository.save(transferred));
    }

    @Override
    @Transactional
    public AllocationDtos.AllocationResponse vacate(UUID studentId) {
        Allocation active = allocationRepository.findByStudentIdAndStatus(studentId, AllocationStatus.ACTIVE)
                .orElseThrow(() -> new BadRequestException("No active allocation found"));
        active.setStatus(AllocationStatus.VACATED);
        active.getBed().setOccupied(false);
        return map(allocationRepository.save(active));
    }

    @Override
    public List<AllocationDtos.AllocationResponse> history(UUID studentId) {
        return allocationRepository.findByStudentIdOrderByCreatedAtDesc(studentId).stream().map(this::map).toList();
    }

    private Bed getAvailableBed(UUID bedId) {
        Bed bed = bedRepository.findById(bedId).orElseThrow(() -> new ResourceNotFoundException("Bed not found"));
        if (bed.isOccupied()) {
            throw new BadRequestException("Bed already occupied");
        }
        return bed;
    }

    private AllocationDtos.AllocationResponse map(Allocation allocation) {
        return new AllocationDtos.AllocationResponse(
                allocation.getId(),
                allocation.getStudent().getId(),
                allocation.getBed().getId(),
                allocation.getStatus().name(),
                allocation.getAllocatedFrom(),
                allocation.getAllocatedTo()
        );
    }
}
