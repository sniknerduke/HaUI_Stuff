package com.qlsv.demo.dto.major;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MajorResponse {

    private Long id;
    private String code;
    private String name;
    private Long facultyId;
    private String facultyName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
