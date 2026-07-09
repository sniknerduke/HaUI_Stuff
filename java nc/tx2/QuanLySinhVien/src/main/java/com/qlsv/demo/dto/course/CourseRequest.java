package com.qlsv.demo.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {

    @NotBlank(message = "Mã học phần không được để trống")
    private String courseCode;

    @NotBlank(message = "Tên học phần không được để trống")
    private String name;

    @NotNull(message = "Số tín chỉ không được để trống")
    @Positive(message = "Số tín chỉ phải lớn hơn 0")
    private Integer credits;

    private String description;
}
