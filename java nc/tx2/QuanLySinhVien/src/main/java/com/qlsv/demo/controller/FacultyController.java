package com.qlsv.demo.controller;

import com.qlsv.demo.dto.ApiResponse;
import com.qlsv.demo.dto.faculty.FacultyRequest;
import com.qlsv.demo.dto.faculty.FacultyResponse;
import com.qlsv.demo.service.FacultyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculties")
@RequiredArgsConstructor
@Tag(name = "Faculty", description = "Quản lý Khoa")
public class FacultyController {

    private final FacultyService facultyService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Tạo khoa mới")
    public ResponseEntity<ApiResponse<FacultyResponse>> create(@Valid @RequestBody FacultyRequest request) {
        return ResponseEntity.ok(ApiResponse.created(facultyService.create(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cập nhật khoa")
    public ResponseEntity<ApiResponse<FacultyResponse>> update(@PathVariable Long id, @Valid @RequestBody FacultyRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", facultyService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Xóa khoa (Soft Delete)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa khoa thành công", null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Xem khoa theo ID")
    public ResponseEntity<ApiResponse<FacultyResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(facultyService.getById(id)));
    }

    @GetMapping
    @Operation(summary = "Danh sách tất cả khoa")
    public ResponseEntity<ApiResponse<List<FacultyResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(facultyService.getAll()));
    }
}
