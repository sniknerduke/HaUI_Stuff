package com.qlsv.demo.dto.major;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MajorRequest {

    @NotBlank(message = "Mã chuyên ngành không được để trống")
    private String code;

    @NotBlank(message = "Tên chuyên ngành không được để trống")
    private String name;

    @NotNull(message = "Khoa không được để trống")
    private Long facultyId;
}
