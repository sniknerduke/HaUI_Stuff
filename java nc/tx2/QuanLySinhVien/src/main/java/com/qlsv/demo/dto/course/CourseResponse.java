package com.qlsv.demo.dto.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponse {

    private Long id;
    private String courseCode;
    private String name;
    private Integer credits;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
