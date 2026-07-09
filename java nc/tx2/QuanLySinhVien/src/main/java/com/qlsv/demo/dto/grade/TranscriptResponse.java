package com.qlsv.demo.dto.grade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TranscriptResponse {

    private Long studentId;
    private String studentCode;
    private String studentName;
    private String className;
    private String majorName;
    private String facultyName;

    private List<GradeResponse> grades;

    private Double gpa;
    private Double gpa4Scale;
    private int totalCredits;
    private int completedCourses;
}
