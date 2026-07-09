package com.qlsv.demo.controller;

import com.qlsv.demo.dto.ApiResponse;
import com.qlsv.demo.dto.grade.GradeRequest;
import com.qlsv.demo.dto.grade.GradeResponse;
import com.qlsv.demo.dto.grade.TranscriptResponse;
import com.qlsv.demo.service.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
@Tag(name = "Grade", description = "Quản lý Điểm số")
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    @Operation(summary = "Nhập/Cập nhật điểm cho sinh viên")
    public ResponseEntity<ApiResponse<GradeResponse>> createOrUpdate(@Valid @RequestBody GradeRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Nhập điểm thành công", gradeService.createOrUpdate(request)));
    }

    @GetMapping("/transcript/{studentId}")
    @Operation(summary = "Xem bảng điểm của sinh viên")
    public ResponseEntity<ApiResponse<TranscriptResponse>> getTranscript(@PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(gradeService.getTranscript(studentId)));
    }
}
