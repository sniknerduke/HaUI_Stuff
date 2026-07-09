package com.qlsv.demo.service;

import com.qlsv.demo.entity.Student;
import com.qlsv.demo.entity.StudentImage;
import com.qlsv.demo.exception.ResourceNotFoundException;
import com.qlsv.demo.repository.StudentImageRepository;
import com.qlsv.demo.repository.StudentRepository;
import com.qlsv.demo.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentImageService {

    private final StudentImageRepository studentImageRepository;
    private final StudentRepository studentRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Transactional
    public StudentImage upload(Long studentId, MultipartFile file, boolean isPrimary) throws IOException {
        Student student = studentRepository.findByIdAndIsDeletedFalse(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Sinh viên", "id", studentId));

        String filePath = FileStorageUtil.saveFile(file, uploadDir);

        if (isPrimary) {
            studentImageRepository.findByStudentIdAndIsPrimaryTrue(studentId)
                    .ifPresent(img -> {
                        img.setIsPrimary(false);
                        studentImageRepository.save(img);
                    });
        }

        StudentImage image = StudentImage.builder()
                .student(student)
                .fileName(file.getOriginalFilename())
                .filePath(filePath)
                .contentType(file.getContentType())
                .fileSize(file.getSize())
                .isPrimary(isPrimary)
                .build();

        return studentImageRepository.save(image);
    }

    public List<StudentImage> getByStudentId(Long studentId) {
        return studentImageRepository.findByStudentId(studentId);
    }

    @Transactional
    public void delete(Long imageId) throws IOException {
        StudentImage image = studentImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Ảnh", "id", imageId));
        FileStorageUtil.deleteFile(image.getFilePath());
        studentImageRepository.delete(image);
    }
}
