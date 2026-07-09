package com.qlsv.demo.dto.enrollment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequest {

    @NotNull(message = "Sinh viên không được để trống")
    private Long studentId;

    @NotNull(message = "Học phần không được để trống")
    private Long courseId;

    @NotBlank(message = "Học kỳ không được để trống")
    private String semester;
}
