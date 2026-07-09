package com.qlsv.demo.controller;

import com.qlsv.demo.dto.ApiResponse;
import com.qlsv.demo.dto.classEntity.ClassRequest;
import com.qlsv.demo.dto.classEntity.ClassResponse;
import com.qlsv.demo.service.ClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
@Tag(name = "Class", description = "Quản lý Lớp học")
public class ClassController {

    private final ClassService classService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Tạo lớp mới")
    public ResponseEntity<ApiResponse<ClassResponse>> create(@Valid @RequestBody ClassRequest request) {
        return ResponseEntity.ok(ApiResponse.created(classService.create(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cập nhật lớp")
    public ResponseEntity<ApiResponse<ClassResponse>> update(@PathVariable Long id, @Valid @RequestBody ClassRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", classService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Xóa lớp (Soft Delete)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        classService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa lớp thành công", null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Xem lớp theo ID")
    public ResponseEntity<ApiResponse<ClassResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(classService.getById(id)));
    }

    @GetMapping
    @Operation(summary = "Danh sách tất cả lớp")
    public ResponseEntity<ApiResponse<List<ClassResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(classService.getAll()));
    }

    @GetMapping("/major/{majorId}")
    @Operation(summary = "Danh sách lớp theo chuyên ngành")
    public ResponseEntity<ApiResponse<List<ClassResponse>>> getByMajor(@PathVariable Long majorId) {
        return ResponseEntity.ok(ApiResponse.success(classService.getByMajorId(majorId)));
    }
}
