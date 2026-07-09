package com.qlsv.demo.controller;

import com.qlsv.demo.dto.ApiResponse;
import com.qlsv.demo.dto.major.MajorRequest;
import com.qlsv.demo.dto.major.MajorResponse;
import com.qlsv.demo.service.MajorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/majors")
@RequiredArgsConstructor
@Tag(name = "Major", description = "Quản lý Chuyên ngành")
public class MajorController {

    private final MajorService majorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Tạo chuyên ngành mới")
    public ResponseEntity<ApiResponse<MajorResponse>> create(@Valid @RequestBody MajorRequest request) {
        return ResponseEntity.ok(ApiResponse.created(majorService.create(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cập nhật chuyên ngành")
    public ResponseEntity<ApiResponse<MajorResponse>> update(@PathVariable Long id, @Valid @RequestBody MajorRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", majorService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Xóa chuyên ngành (Soft Delete)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        majorService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa chuyên ngành thành công", null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Xem chuyên ngành theo ID")
    public ResponseEntity<ApiResponse<MajorResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(majorService.getById(id)));
    }

    @GetMapping
    @Operation(summary = "Danh sách tất cả chuyên ngành")
    public ResponseEntity<ApiResponse<List<MajorResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(majorService.getAll()));
    }

    @GetMapping("/faculty/{facultyId}")
    @Operation(summary = "Danh sách chuyên ngành theo khoa")
    public ResponseEntity<ApiResponse<List<MajorResponse>>> getByFaculty(@PathVariable Long facultyId) {
        return ResponseEntity.ok(ApiResponse.success(majorService.getByFacultyId(facultyId)));
    }
}
