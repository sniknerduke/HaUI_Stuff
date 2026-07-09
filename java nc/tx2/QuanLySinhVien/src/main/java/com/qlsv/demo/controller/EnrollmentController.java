package com.qlsv.demo.controller;

import com.qlsv.demo.dto.ApiResponse;
import com.qlsv.demo.dto.enrollment.EnrollmentRequest;
import com.qlsv.demo.dto.enrollment.EnrollmentResponse;
import com.qlsv.demo.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollment", description = "Quản lý Đăng ký học phần")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    @Operation(summary = "Đăng ký học phần cho sinh viên")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> enroll(@Valid @RequestBody EnrollmentRequest request) {
        return ResponseEntity.ok(ApiResponse.created(enrollmentService.enroll(request)));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Xem danh sách đăng ký của sinh viên")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(enrollmentService.getByStudentId(studentId)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Xem tất cả đăng ký (Admin)")
    public ResponseEntity<ApiResponse<Page<EnrollmentResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(enrollmentService.getAll(PageRequest.of(page, size))));
    }
}
