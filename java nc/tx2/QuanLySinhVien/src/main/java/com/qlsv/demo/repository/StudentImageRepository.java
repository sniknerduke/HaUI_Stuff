package com.qlsv.demo.repository;

import com.qlsv.demo.entity.StudentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentImageRepository extends JpaRepository<StudentImage, Long> {

    List<StudentImage> findByStudentId(Long studentId);

    Optional<StudentImage> findByStudentIdAndIsPrimaryTrue(Long studentId);
}
