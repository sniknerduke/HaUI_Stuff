package com.qlsv.demo.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticsResponse {

    private long totalStudents;
    private List<FacultyStudentCount> studentsByFaculty;
    private long passedCount;
    private long failedCount;
    private double passRate;
    private double failRate;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FacultyStudentCount {
        private Long facultyId;
        private String facultyName;
        private long studentCount;
    }
}
