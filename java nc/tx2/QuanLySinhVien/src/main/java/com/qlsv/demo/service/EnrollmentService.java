package com.qlsv.demo.service;

import com.qlsv.demo.dto.enrollment.EnrollmentRequest;
import com.qlsv.demo.dto.enrollment.EnrollmentResponse;
import com.qlsv.demo.entity.Course;
import com.qlsv.demo.entity.Enrollment;
import com.qlsv.demo.entity.Student;
import com.qlsv.demo.exception.DuplicateResourceException;
import com.qlsv.demo.exception.ResourceNotFoundException;
import com.qlsv.demo.mapper.EnrollmentMapper;
import com.qlsv.demo.repository.CourseRepository;
import com.qlsv.demo.repository.EnrollmentRepository;
import com.qlsv.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Transactional
    public EnrollmentResponse enroll(EnrollmentRequest request) {
        Student student = studentRepository.findByIdAndIsDeletedFalse(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Sinh viên", "id", request.getStudentId()));
        Course course = courseRepository.findByIdAndIsDeletedFalse(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Học phần", "id", request.getCourseId()));

        if (enrollmentRepository.existsByStudentIdAndCourseIdAndSemester(
                request.getStudentId(), request.getCourseId(), request.getSemester())) {
            throw new DuplicateResourceException("Sinh viên đã đăng ký học phần này trong học kỳ " + request.getSemester());
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .semester(request.getSemester())
                .build();
        enrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toResponse(enrollment);
    }

    public List<EnrollmentResponse> getByStudentId(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollmentMapper.toResponseList(enrollments);
    }

    public Page<EnrollmentResponse> getAll(Pageable pageable) {
        return enrollmentRepository.findAll(pageable).map(enrollmentMapper::toResponse);
    }
}
