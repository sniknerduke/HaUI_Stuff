package com.qlsv.demo.service;

import com.qlsv.demo.dto.PageResponse;
import com.qlsv.demo.dto.student.StudentRequest;
import com.qlsv.demo.dto.student.StudentResponse;
import com.qlsv.demo.dto.student.StudentSearchRequest;
import com.qlsv.demo.entity.ClassEntity;
import com.qlsv.demo.entity.Student;
import com.qlsv.demo.exception.DuplicateResourceException;
import com.qlsv.demo.exception.ResourceNotFoundException;
import com.qlsv.demo.mapper.StudentMapper;
import com.qlsv.demo.repository.ClassEntityRepository;
import com.qlsv.demo.repository.StudentRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final ClassEntityRepository classEntityRepository;
    private final StudentMapper studentMapper;

    @Transactional
    public StudentResponse create(StudentRequest request) {
        if (studentRepository.existsByStudentCode(request.getStudentCode())) {
            throw new DuplicateResourceException("Sinh viên", "mã SV", request.getStudentCode());
        }
        if (request.getEmail() != null && studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Sinh viên", "email", request.getEmail());
        }
        ClassEntity classEntity = classEntityRepository.findByIdAndIsDeletedFalse(request.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Lớp", "id", request.getClassId()));

        Student student = Student.builder()
                .studentCode(request.getStudentCode())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .classEntity(classEntity)
                .status(request.getStatus())
                .build();

        student = studentRepository.save(student);
        return studentMapper.toResponse(student);
    }

    @Transactional
    public StudentResponse update(Long id, StudentRequest request) {
        Student student = studentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sinh viên", "id", id));
        ClassEntity classEntity = classEntityRepository.findByIdAndIsDeletedFalse(request.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Lớp", "id", request.getClassId()));

        student.setStudentCode(request.getStudentCode());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setAddress(request.getAddress());
        student.setClassEntity(classEntity);
        if (request.getStatus() != null) {
            student.setStatus(request.getStatus());
        }

        student = studentRepository.save(student);
        return studentMapper.toResponse(student);
    }

    @Transactional
    public void delete(Long id) {
        Student student = studentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sinh viên", "id", id));
        student.setIsDeleted(true);
        studentRepository.save(student);
    }

    public StudentResponse getById(Long id) {
        Student student = studentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sinh viên", "id", id));
        return studentMapper.toResponse(student);
    }

    public StudentResponse getByCode(String code) {
        Student student = studentRepository.findByStudentCodeAndIsDeletedFalse(code)
                .orElseThrow(() -> new ResourceNotFoundException("Sinh viên", "mã SV", code));
        return studentMapper.toResponse(student);
    }

    public PageResponse<StudentResponse> search(StudentSearchRequest request) {
        Sort sort = request.getSortDir().equalsIgnoreCase("desc")
                ? Sort.by(request.getSortBy()).descending()
                : Sort.by(request.getSortBy()).ascending();
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        Specification<Student> spec = buildSpecification(request);
        Page<Student> page = studentRepository.findAll(spec, pageable);

        List<StudentResponse> content = studentMapper.toResponseList(page.getContent());

        return PageResponse.<StudentResponse>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    public List<StudentResponse> getAllActive() {
        Specification<Student> spec = (root, query, cb) -> cb.equal(root.get("isDeleted"), false);
        return studentMapper.toResponseList(studentRepository.findAll(spec));
    }

    private Specification<Student> buildSpecification(StudentSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Always exclude soft-deleted
            predicates.add(cb.equal(root.get("isDeleted"), false));

            if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                String keyword = "%" + request.getKeyword().toLowerCase() + "%";
                Predicate keywordPredicate = cb.or(
                        cb.like(cb.lower(root.get("firstName")), keyword),
                        cb.like(cb.lower(root.get("lastName")), keyword),
                        cb.like(cb.lower(root.get("email")), keyword),
                        cb.like(root.get("phone"), keyword),
                        cb.like(root.get("studentCode"), keyword)
                );
                predicates.add(keywordPredicate);
            }

            if (request.getClassId() != null) {
                predicates.add(cb.equal(root.get("classEntity").get("id"), request.getClassId()));
            }

            if (request.getMajorId() != null) {
                predicates.add(cb.equal(root.get("classEntity").get("major").get("id"), request.getMajorId()));
            }

            if (request.getFacultyId() != null) {
                predicates.add(cb.equal(root.get("classEntity").get("major").get("faculty").get("id"), request.getFacultyId()));
            }

            if (request.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), request.getGender()));
            }

            if (request.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), request.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
