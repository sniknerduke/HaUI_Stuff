package com.qlsv.demo.mapper;

import com.qlsv.demo.dto.student.StudentResponse;
import com.qlsv.demo.entity.Student;
import com.qlsv.demo.entity.StudentImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "fullName", expression = "java(student.getLastName() + \" \" + student.getFirstName())")
    @Mapping(target = "classId", source = "classEntity.id")
    @Mapping(target = "className", source = "classEntity.name")
    @Mapping(target = "majorName", source = "classEntity.major.name")
    @Mapping(target = "facultyName", source = "classEntity.major.faculty.name")
    @Mapping(target = "imageUrl", source = "images", qualifiedByName = "getPrimaryImageUrl")
    StudentResponse toResponse(Student student);

    List<StudentResponse> toResponseList(List<Student> students);

    @Named("getPrimaryImageUrl")
    default String getPrimaryImageUrl(List<StudentImage> images) {
        if (images == null || images.isEmpty()) return null;
        return images.stream()
                .filter(img -> Boolean.TRUE.equals(img.getIsPrimary()))
                .findFirst()
                .map(img -> "/api/students/images/" + img.getId())
                .orElse(images.isEmpty() ? null : "/api/students/images/" + images.getFirst().getId());
    }
}
