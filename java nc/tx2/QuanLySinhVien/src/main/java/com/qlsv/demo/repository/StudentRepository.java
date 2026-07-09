package com.qlsv.demo.repository;

import com.qlsv.demo.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    Optional<Student> findByIdAndIsDeletedFalse(Long id);

    Optional<Student> findByStudentCodeAndIsDeletedFalse(String studentCode);

    boolean existsByStudentCode(String studentCode);

    boolean existsByEmail(String email);

    Page<Student> findAllByIsDeletedFalse(Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.isDeleted = false AND " +
            "(LOWER(s.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "s.phone LIKE CONCAT('%', :keyword, '%') OR " +
            "s.studentCode LIKE CONCAT('%', :keyword, '%'))")
    Page<Student> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.isDeleted = false AND s.classEntity.major.faculty.id = :facultyId")
    List<Student> findByFacultyId(@Param("facultyId") Long facultyId);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.isDeleted = false AND s.classEntity.major.faculty.id = :facultyId")
    Long countByFacultyId(@Param("facultyId") Long facultyId);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.isDeleted = false")
    Long countActiveStudents();
}
