package com.qlsv.demo.service;

import com.qlsv.demo.dto.major.MajorRequest;
import com.qlsv.demo.dto.major.MajorResponse;
import com.qlsv.demo.entity.Faculty;
import com.qlsv.demo.entity.Major;
import com.qlsv.demo.exception.DuplicateResourceException;
import com.qlsv.demo.exception.ResourceNotFoundException;
import com.qlsv.demo.mapper.MajorMapper;
import com.qlsv.demo.repository.FacultyRepository;
import com.qlsv.demo.repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MajorService {

    private final MajorRepository majorRepository;
    private final FacultyRepository facultyRepository;
    private final MajorMapper majorMapper;

    @Transactional
    public MajorResponse create(MajorRequest request) {
        if (majorRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Chuyên ngành", "mã", request.getCode());
        }
        Faculty faculty = facultyRepository.findByIdAndIsDeletedFalse(request.getFacultyId())
                .orElseThrow(() -> new ResourceNotFoundException("Khoa", "id", request.getFacultyId()));

        Major major = Major.builder()
                .code(request.getCode())
                .name(request.getName())
                .faculty(faculty)
                .build();
        major = majorRepository.save(major);
        return majorMapper.toResponse(major);
    }

    @Transactional
    public MajorResponse update(Long id, MajorRequest request) {
        Major major = majorRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chuyên ngành", "id", id));
        Faculty faculty = facultyRepository.findByIdAndIsDeletedFalse(request.getFacultyId())
                .orElseThrow(() -> new ResourceNotFoundException("Khoa", "id", request.getFacultyId()));

        major.setCode(request.getCode());
        major.setName(request.getName());
        major.setFaculty(faculty);
        major = majorRepository.save(major);
        return majorMapper.toResponse(major);
    }

    @Transactional
    public void delete(Long id) {
        Major major = majorRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chuyên ngành", "id", id));
        major.setIsDeleted(true);
        majorRepository.save(major);
    }

    public MajorResponse getById(Long id) {
        Major major = majorRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chuyên ngành", "id", id));
        return majorMapper.toResponse(major);
    }

    public List<MajorResponse> getAll() {
        return majorMapper.toResponseList(majorRepository.findAllByIsDeletedFalse());
    }

    public List<MajorResponse> getByFacultyId(Long facultyId) {
        return majorMapper.toResponseList(majorRepository.findByFacultyIdAndIsDeletedFalse(facultyId));
    }
}
