package com.hostel.management.controller;

import com.hostel.management.dto.StudentDtos;
import com.hostel.management.service.StudentService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasAnyRole('ADMIN','WARDEN')")
    @PostMapping
    public ResponseEntity<StudentDtos.StudentResponse> create(@Valid @RequestBody StudentDtos.StudentUpsertRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.create(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','WARDEN')")
    @PutMapping("/{id}")
    public ResponseEntity<StudentDtos.StudentResponse> update(@PathVariable UUID id,
                                                              @Valid @RequestBody StudentDtos.StudentUpsertRequest request) {
        return ResponseEntity.ok(studentService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','WARDEN')")
    @GetMapping
    public ResponseEntity<List<StudentDtos.StudentResponse>> list() {
        return ResponseEntity.ok(studentService.list());
    }
}
