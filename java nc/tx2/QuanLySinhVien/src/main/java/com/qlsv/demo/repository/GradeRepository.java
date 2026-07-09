package com.qlsv.demo.repository;

import com.qlsv.demo.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    Optional<Grade> findByEnrollmentId(Long enrollmentId);

    @Query("SELECT g FROM Grade g WHERE g.enrollment.student.id = :studentId")
    List<Grade> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT AVG(g.totalScore) FROM Grade g WHERE g.enrollment.student.id = :studentId AND g.totalScore IS NOT NULL")
    Double calculateAverageScore(@Param("studentId") Long studentId);

    @Query("SELECT COUNT(g) FROM Grade g WHERE g.totalScore IS NOT NULL AND g.totalScore >= 5.0")
    Long countPassedGrades();

    @Query("SELECT COUNT(g) FROM Grade g WHERE g.totalScore IS NOT NULL AND g.totalScore < 5.0")
    Long countFailedGrades();

    @Query("SELECT COUNT(g) FROM Grade g WHERE g.totalScore IS NOT NULL")
    Long countGradedEntries();
}
