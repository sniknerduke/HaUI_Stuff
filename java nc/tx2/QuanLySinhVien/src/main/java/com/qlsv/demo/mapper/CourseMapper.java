package com.qlsv.demo.mapper;

import com.qlsv.demo.dto.course.CourseRequest;
import com.qlsv.demo.dto.course.CourseResponse;
import com.qlsv.demo.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseResponse toResponse(Course course);

    List<CourseResponse> toResponseList(List<Course> courses);

    Course toEntity(CourseRequest request);

    void updateEntity(CourseRequest request, @MappingTarget Course course);
}
