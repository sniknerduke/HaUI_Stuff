package com.qlsv.demo.service;

import com.qlsv.demo.dto.course.CourseRequest;
import com.qlsv.demo.dto.course.CourseResponse;
import com.qlsv.demo.entity.Course;
import com.qlsv.demo.exception.DuplicateResourceException;
import com.qlsv.demo.exception.ResourceNotFoundException;
import com.qlsv.demo.mapper.CourseMapper;
import com.qlsv.demo.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Transactional
    public CourseResponse create(CourseRequest request) {
        if (courseRepository.existsByCourseCode(request.getCourseCode())) {
            throw new DuplicateResourceException("Học phần", "mã", request.getCourseCode());
        }
        Course course = courseMapper.toEntity(request);
        course = courseRepository.save(course);
        return courseMapper.toResponse(course);
    }

    @Transactional
    public CourseResponse update(Long id, CourseRequest request) {
        Course course = courseRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Học phần", "id", id));
        courseMapper.updateEntity(request, course);
        course = courseRepository.save(course);
        return courseMapper.toResponse(course);
    }

    @Transactional
    public void delete(Long id) {
        Course course = courseRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Học phần", "id", id));
        course.setIsDeleted(true);
        courseRepository.save(course);
    }

    public CourseResponse getById(Long id) {
        Course course = courseRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Học phần", "id", id));
        return courseMapper.toResponse(course);
    }

    public List<CourseResponse> getAll() {
        return courseMapper.toResponseList(courseRepository.findAllByIsDeletedFalse());
    }
}
