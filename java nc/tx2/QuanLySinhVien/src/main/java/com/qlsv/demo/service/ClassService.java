package com.qlsv.demo.service;

import com.qlsv.demo.dto.classEntity.ClassRequest;
import com.qlsv.demo.dto.classEntity.ClassResponse;
import com.qlsv.demo.entity.ClassEntity;
import com.qlsv.demo.entity.Major;
import com.qlsv.demo.exception.DuplicateResourceException;
import com.qlsv.demo.exception.ResourceNotFoundException;
import com.qlsv.demo.mapper.ClassMapper;
import com.qlsv.demo.repository.ClassEntityRepository;
import com.qlsv.demo.repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassEntityRepository classEntityRepository;
    private final MajorRepository majorRepository;
    private final ClassMapper classMapper;

    @Transactional
    public ClassResponse create(ClassRequest request) {
        if (classEntityRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Lớp", "mã", request.getCode());
        }
        Major major = majorRepository.findByIdAndIsDeletedFalse(request.getMajorId())
                .orElseThrow(() -> new ResourceNotFoundException("Chuyên ngành", "id", request.getMajorId()));

        ClassEntity classEntity = ClassEntity.builder()
                .code(request.getCode())
                .name(request.getName())
                .major(major)
                .academicYear(request.getAcademicYear())
                .build();
        classEntity = classEntityRepository.save(classEntity);
        return classMapper.toResponse(classEntity);
    }

    @Transactional
    public ClassResponse update(Long id, ClassRequest request) {
        ClassEntity classEntity = classEntityRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lớp", "id", id));
        Major major = majorRepository.findByIdAndIsDeletedFalse(request.getMajorId())
                .orElseThrow(() -> new ResourceNotFoundException("Chuyên ngành", "id", request.getMajorId()));

        classEntity.setCode(request.getCode());
        classEntity.setName(request.getName());
        classEntity.setMajor(major);
        classEntity.setAcademicYear(request.getAcademicYear());
        classEntity = classEntityRepository.save(classEntity);
        return classMapper.toResponse(classEntity);
    }

    @Transactional
    public void delete(Long id) {
        ClassEntity classEntity = classEntityRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lớp", "id", id));
        classEntity.setIsDeleted(true);
        classEntityRepository.save(classEntity);
    }

    public ClassResponse getById(Long id) {
        ClassEntity classEntity = classEntityRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lớp", "id", id));
        return classMapper.toResponse(classEntity);
    }

    public List<ClassResponse> getAll() {
        return classMapper.toResponseList(classEntityRepository.findAllByIsDeletedFalse());
    }

    public List<ClassResponse> getByMajorId(Long majorId) {
        return classMapper.toResponseList(classEntityRepository.findByMajorIdAndIsDeletedFalse(majorId));
    }
}
