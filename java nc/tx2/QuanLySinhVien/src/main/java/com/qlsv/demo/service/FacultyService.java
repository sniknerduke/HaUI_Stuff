package com.qlsv.demo.service;

import com.qlsv.demo.dto.faculty.FacultyRequest;
import com.qlsv.demo.dto.faculty.FacultyResponse;
import com.qlsv.demo.entity.Faculty;
import com.qlsv.demo.exception.DuplicateResourceException;
import com.qlsv.demo.exception.ResourceNotFoundException;
import com.qlsv.demo.mapper.FacultyMapper;
import com.qlsv.demo.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    @Transactional
    public FacultyResponse create(FacultyRequest request) {
        if (facultyRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Khoa", "mã", request.getCode());
        }
        Faculty faculty = facultyMapper.toEntity(request);
        faculty = facultyRepository.save(faculty);
        return facultyMapper.toResponse(faculty);
    }

    @Transactional
    public FacultyResponse update(Long id, FacultyRequest request) {
        Faculty faculty = facultyRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khoa", "id", id));
        facultyMapper.updateEntity(request, faculty);
        faculty = facultyRepository.save(faculty);
        return facultyMapper.toResponse(faculty);
    }

    @Transactional
    public void delete(Long id) {
        Faculty faculty = facultyRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khoa", "id", id));
        faculty.setIsDeleted(true);
        facultyRepository.save(faculty);
    }

    public FacultyResponse getById(Long id) {
        Faculty faculty = facultyRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khoa", "id", id));
        return facultyMapper.toResponse(faculty);
    }

    public List<FacultyResponse> getAll() {
        return facultyMapper.toResponseList(facultyRepository.findAllByIsDeletedFalse());
    }
}
