package com.qlsv.demo.repository;

import com.qlsv.demo.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {

    Optional<Major> findByIdAndIsDeletedFalse(Long id);

    List<Major> findAllByIsDeletedFalse();

    List<Major> findByFacultyIdAndIsDeletedFalse(Long facultyId);

    boolean existsByCode(String code);
}
