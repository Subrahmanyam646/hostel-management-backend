package com.hostel.management.service;

import com.hostel.management.dto.StudentDtos;
import java.util.List;
import java.util.UUID;

public interface StudentService {
    StudentDtos.StudentResponse create(StudentDtos.StudentUpsertRequest request);
    StudentDtos.StudentResponse update(UUID id, StudentDtos.StudentUpsertRequest request);
    void delete(UUID id);
    List<StudentDtos.StudentResponse> list();
}
