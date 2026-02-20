package com.hostel.management.service.impl;

import com.hostel.management.dto.StudentDtos;
import com.hostel.management.entity.Student;
import com.hostel.management.exception.ResourceNotFoundException;
import com.hostel.management.repository.StudentRepository;
import com.hostel.management.repository.UserRepository;
import com.hostel.management.service.StudentService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Override
    public StudentDtos.StudentResponse create(StudentDtos.StudentUpsertRequest request) {
        Student student = new Student();
        return saveAndMap(student, request);
    }

    @Override
    public StudentDtos.StudentResponse update(UUID id, StudentDtos.StudentUpsertRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return saveAndMap(student, request);
    }

    @Override
    public void delete(UUID id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<StudentDtos.StudentResponse> list() {
        return studentRepository.findAll().stream().map(this::map).toList();
    }

    private StudentDtos.StudentResponse saveAndMap(Student student, StudentDtos.StudentUpsertRequest request) {
        var user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        student.setUser(user);
        student.setPhone(request.phone());
        student.setGuardianName(request.guardianName());
        student.setGuardianPhone(request.guardianPhone());
        student.setEnrollmentNumber(request.enrollmentNumber());
        return map(studentRepository.save(student));
    }

    private StudentDtos.StudentResponse map(Student student) {
        return new StudentDtos.StudentResponse(
                student.getId(),
                student.getUser().getId(),
                student.getUser().getFullName(),
                student.getUser().getEmail(),
                student.getPhone(),
                student.getGuardianName(),
                student.getGuardianPhone(),
                student.getEnrollmentNumber()
        );
    }
}
