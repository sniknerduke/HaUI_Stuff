package com.qlsv.demo.dto.enrollment;

import com.qlsv.demo.entity.enums.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentResponse {

    private Long id;
    private Long studentId;
    private String studentCode;
    private String studentName;
    private Long courseId;
    private String courseCode;
    private String courseName;
    private Integer credits;
    private String semester;
    private EnrollmentStatus status;
    private LocalDateTime enrolledAt;
}
