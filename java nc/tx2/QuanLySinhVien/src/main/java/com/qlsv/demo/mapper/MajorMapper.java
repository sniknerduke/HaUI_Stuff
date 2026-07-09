package com.qlsv.demo.mapper;

import com.qlsv.demo.dto.major.MajorResponse;
import com.qlsv.demo.entity.Major;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MajorMapper {

    @Mapping(target = "facultyId", source = "faculty.id")
    @Mapping(target = "facultyName", source = "faculty.name")
    MajorResponse toResponse(Major major);

    List<MajorResponse> toResponseList(List<Major> majors);
}
