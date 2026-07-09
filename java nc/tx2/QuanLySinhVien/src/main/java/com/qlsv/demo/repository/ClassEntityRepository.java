package com.qlsv.demo.repository;

import com.qlsv.demo.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassEntityRepository extends JpaRepository<ClassEntity, Long> {

    Optional<ClassEntity> findByIdAndIsDeletedFalse(Long id);

    List<ClassEntity> findAllByIsDeletedFalse();

    List<ClassEntity> findByMajorIdAndIsDeletedFalse(Long majorId);

    boolean existsByCode(String code);
}
