package com.qlsv.demo.mapper;

import com.qlsv.demo.dto.enrollment.EnrollmentResponse;
import com.qlsv.demo.entity.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentCode", source = "student.studentCode")
    @Mapping(target = "studentName", expression = "java(enrollment.getStudent().getLastName() + \" \" + enrollment.getStudent().getFirstName())")
    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "courseCode", source = "course.courseCode")
    @Mapping(target = "courseName", source = "course.name")
    @Mapping(target = "credits", source = "course.credits")
    EnrollmentResponse toResponse(Enrollment enrollment);

    List<EnrollmentResponse> toResponseList(List<Enrollment> enrollments);
}
