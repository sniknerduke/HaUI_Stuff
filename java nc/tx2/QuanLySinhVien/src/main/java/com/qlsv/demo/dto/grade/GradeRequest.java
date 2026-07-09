package com.qlsv.demo.dto.grade;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeRequest {

    @NotNull(message = "Enrollment ID không được để trống")
    private Long enrollmentId;

    @NotNull(message = "Điểm giữa kỳ không được để trống")
    @DecimalMin(value = "0.0", message = "Điểm giữa kỳ phải >= 0")
    @DecimalMax(value = "10.0", message = "Điểm giữa kỳ phải <= 10")
    private Double midtermScore;

    @NotNull(message = "Điểm cuối kỳ không được để trống")
    @DecimalMin(value = "0.0", message = "Điểm cuối kỳ phải >= 0")
    @DecimalMax(value = "10.0", message = "Điểm cuối kỳ phải <= 10")
    private Double finalScore;
}
