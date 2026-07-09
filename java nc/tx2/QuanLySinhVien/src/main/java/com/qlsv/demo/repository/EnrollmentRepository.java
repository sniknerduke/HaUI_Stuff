package com.qlsv.demo.repository;

import com.qlsv.demo.entity.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByCourseIdAndSemester(Long courseId, String semester);

    Optional<Enrollment> findByStudentIdAndCourseIdAndSemester(Long studentId, Long courseId, String semester);

    Page<Enrollment> findAll(Pageable pageable);

    boolean existsByStudentIdAndCourseIdAndSemester(Long studentId, Long courseId, String semester);
}
