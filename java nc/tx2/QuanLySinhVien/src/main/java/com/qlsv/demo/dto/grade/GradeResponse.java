package com.qlsv.demo.dto.grade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeResponse {

    private Long id;
    private Long enrollmentId;
    private String courseCode;
    private String courseName;
    private Integer credits;
    private String semester;
    private Double midtermScore;
    private Double finalScore;
    private Double totalScore;
    private String letterGrade;
}
