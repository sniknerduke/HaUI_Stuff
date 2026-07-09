package com.qlsv.demo.dto.classEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassResponse {

    private Long id;
    private String code;
    private String name;
    private Long majorId;
    private String majorName;
    private String facultyName;
    private Integer academicYear;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
