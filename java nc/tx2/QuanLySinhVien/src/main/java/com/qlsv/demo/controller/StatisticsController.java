package com.qlsv.demo.controller;

import com.qlsv.demo.dto.ApiResponse;
import com.qlsv.demo.dto.statistics.StatisticsResponse;
import com.qlsv.demo.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Statistics", description = "Thống kê")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    @Operation(summary = "Thống kê tổng hợp: SV theo khoa, tỷ lệ đậu/rớt")
    public ResponseEntity<ApiResponse<StatisticsResponse>> getStatistics() {
        return ResponseEntity.ok(ApiResponse.success(statisticsService.getStatistics()));
    }
}
