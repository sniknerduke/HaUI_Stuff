package com.qlsv.demo.mapper;

import com.qlsv.demo.dto.faculty.FacultyRequest;
import com.qlsv.demo.dto.faculty.FacultyResponse;
import com.qlsv.demo.entity.Faculty;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FacultyMapper {

    FacultyResponse toResponse(Faculty faculty);

    List<FacultyResponse> toResponseList(List<Faculty> faculties);

    Faculty toEntity(FacultyRequest request);

    void updateEntity(FacultyRequest request, @MappingTarget Faculty faculty);
}
