package com.qlsv.demo.dto.student;

import com.qlsv.demo.entity.enums.Gender;
import com.qlsv.demo.entity.enums.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentSearchRequest {

    private String keyword;
    private Long classId;
    private Long facultyId;
    private Long majorId;
    private Gender gender;
    private StudentStatus status;

    @Builder.Default
    private int page = 0;
    @Builder.Default
    private int size = 10;
    @Builder.Default
    private String sortBy = "id";
    @Builder.Default
    private String sortDir = "asc";
}
