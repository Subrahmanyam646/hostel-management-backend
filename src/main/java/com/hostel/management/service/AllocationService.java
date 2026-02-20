package com.hostel.management.service;

import com.hostel.management.dto.AllocationDtos;
import java.util.List;
import java.util.UUID;

public interface AllocationService {
    AllocationDtos.AllocationResponse allocate(AllocationDtos.AllocateRequest request);
    AllocationDtos.AllocationResponse transfer(AllocationDtos.TransferRequest request);
    AllocationDtos.AllocationResponse vacate(UUID studentId);
    List<AllocationDtos.AllocationResponse> history(UUID studentId);
}
