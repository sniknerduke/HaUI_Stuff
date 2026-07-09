package com.qlsv.demo.mapper;

import com.qlsv.demo.dto.classEntity.ClassResponse;
import com.qlsv.demo.entity.ClassEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassMapper {

    @Mapping(target = "majorId", source = "major.id")
    @Mapping(target = "majorName", source = "major.name")
    @Mapping(target = "facultyName", source = "major.faculty.name")
    ClassResponse toResponse(ClassEntity classEntity);

    List<ClassResponse> toResponseList(List<ClassEntity> classEntities);
}
