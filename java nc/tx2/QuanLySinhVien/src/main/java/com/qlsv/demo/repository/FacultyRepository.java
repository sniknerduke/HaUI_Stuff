package com.qlsv.demo.repository;

import com.qlsv.demo.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Optional<Faculty> findByIdAndIsDeletedFalse(Long id);

    Optional<Faculty> findByCodeAndIsDeletedFalse(String code);

    List<Faculty> findAllByIsDeletedFalse();

    boolean existsByCode(String code);
}
