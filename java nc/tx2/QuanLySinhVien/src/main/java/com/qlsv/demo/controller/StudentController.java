package com.qlsv.demo.controller;

import com.qlsv.demo.dto.ApiResponse;
import com.qlsv.demo.dto.PageResponse;
import com.qlsv.demo.dto.student.StudentRequest;
import com.qlsv.demo.dto.student.StudentResponse;
import com.qlsv.demo.dto.student.StudentSearchRequest;
import com.qlsv.demo.entity.StudentImage;
import com.qlsv.demo.entity.enums.Gender;
import com.qlsv.demo.entity.enums.StudentStatus;
import com.qlsv.demo.service.ExcelExportService;
import com.qlsv.demo.service.StudentImageService;
import com.qlsv.demo.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Student", description = "Quản lý sinh viên")
public class StudentController {

    private final StudentService studentService;
    private final StudentImageService studentImageService;
    private final ExcelExportService excelExportService;

    @PostMapping
    @Operation(summary = "Thêm sinh viên mới")
    public ResponseEntity<ApiResponse<StudentResponse>> create(@Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(ApiResponse.created(studentService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật thông tin sinh viên")
    public ResponseEntity<ApiResponse<StudentResponse>> update(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", studentService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Xóa sinh viên (Soft Delete)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa sinh viên thành công", null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Xem thông tin sinh viên theo ID")
    public ResponseEntity<ApiResponse<StudentResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(studentService.getById(id)));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Tìm sinh viên theo mã SV")
    public ResponseEntity<ApiResponse<StudentResponse>> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(ApiResponse.success(studentService.getByCode(code)));
    }

    @GetMapping("/search")
    @Operation(summary = "Tìm kiếm & lọc sinh viên (phân trang)")
    public ResponseEntity<ApiResponse<PageResponse<StudentResponse>>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Long facultyId,
            @RequestParam(required = false) Long majorId,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) StudentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        StudentSearchRequest searchRequest = StudentSearchRequest.builder()
                .keyword(keyword).classId(classId).facultyId(facultyId).majorId(majorId)
                .gender(gender).status(status).page(page).size(size).sortBy(sortBy).sortDir(sortDir)
                .build();

        return ResponseEntity.ok(ApiResponse.success(studentService.search(searchRequest)));
    }

    @PostMapping("/{id}/images")
    @Operation(summary = "Upload ảnh sinh viên")
    public ResponseEntity<ApiResponse<Map<String, Object>>> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "false") boolean isPrimary) throws IOException {

        StudentImage image = studentImageService.upload(id, file, isPrimary);
        Map<String, Object> data = Map.of(
                "imageId", image.getId(),
                "fileName", image.getFileName(),
                "url", "/api/students/images/" + image.getId()
        );
        return ResponseEntity.ok(ApiResponse.created(data));
    }

    @GetMapping("/{id}/images")
    @Operation(summary = "Xem danh sách ảnh sinh viên")
    public ResponseEntity<ApiResponse<List<StudentImage>>> getImages(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(studentImageService.getByStudentId(id)));
    }

    @GetMapping("/export/excel")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Export danh sách sinh viên ra Excel")
    public ResponseEntity<InputStreamResource> exportExcel() throws IOException {
        ByteArrayInputStream in = excelExportService.exportStudents();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=danh-sach-sinh-vien.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }
}
