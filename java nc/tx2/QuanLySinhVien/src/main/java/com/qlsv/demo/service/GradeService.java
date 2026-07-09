package com.qlsv.demo.service;

import com.qlsv.demo.dto.grade.GradeRequest;
import com.qlsv.demo.dto.grade.GradeResponse;
import com.qlsv.demo.dto.grade.TranscriptResponse;
import com.qlsv.demo.entity.Enrollment;
import com.qlsv.demo.entity.Grade;
import com.qlsv.demo.entity.Student;
import com.qlsv.demo.exception.ResourceNotFoundException;
import com.qlsv.demo.repository.EnrollmentRepository;
import com.qlsv.demo.repository.GradeRepository;
import com.qlsv.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public GradeResponse createOrUpdate(GradeRequest request) {
        Enrollment enrollment = enrollmentRepository.findById(request.getEnrollmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Đăng ký học phần", "id", request.getEnrollmentId()));

        Optional<Grade> existingGrade = gradeRepository.findByEnrollmentId(request.getEnrollmentId());
        Grade grade;
        if (existingGrade.isPresent()) {
            grade = existingGrade.get();
            grade.setMidtermScore(request.getMidtermScore());
            grade.setFinalScore(request.getFinalScore());
        } else {
            grade = Grade.builder()
                    .enrollment(enrollment)
                    .midtermScore(request.getMidtermScore())
                    .finalScore(request.getFinalScore())
                    .build();
            grade.calculateTotalScore();
        }
        grade = gradeRepository.save(grade);
        return toGradeResponse(grade);
    }

    public TranscriptResponse getTranscript(Long studentId) {
        Student student = studentRepository.findByIdAndIsDeletedFalse(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Sinh viên", "id", studentId));

        List<Grade> grades = gradeRepository.findByStudentId(studentId);
        List<GradeResponse> gradeResponses = grades.stream().map(this::toGradeResponse).toList();

        Double avgScore = gradeRepository.calculateAverageScore(studentId);
        double gpa = avgScore != null ? Math.round(avgScore * 100.0) / 100.0 : 0.0;

        int totalCredits = grades.stream()
                .filter(g -> g.getTotalScore() != null && g.getTotalScore() >= 4.0)
                .mapToInt(g -> g.getEnrollment().getCourse().getCredits())
                .sum();

        return TranscriptResponse.builder()
                .studentId(student.getId())
                .studentCode(student.getStudentCode())
                .studentName(student.getLastName() + " " + student.getFirstName())
                .className(student.getClassEntity() != null ? student.getClassEntity().getName() : null)
                .majorName(student.getClassEntity() != null && student.getClassEntity().getMajor() != null
                        ? student.getClassEntity().getMajor().getName() : null)
                .facultyName(student.getClassEntity() != null && student.getClassEntity().getMajor() != null
                        && student.getClassEntity().getMajor().getFaculty() != null
                        ? student.getClassEntity().getMajor().getFaculty().getName() : null)
                .grades(gradeResponses)
                .gpa(gpa)
                .gpa4Scale(convertTo4Scale(gpa))
                .totalCredits(totalCredits)
                .completedCourses(gradeResponses.size())
                .build();
    }

    private GradeResponse toGradeResponse(Grade grade) {
        return GradeResponse.builder()
                .id(grade.getId())
                .enrollmentId(grade.getEnrollment().getId())
                .courseCode(grade.getEnrollment().getCourse().getCourseCode())
                .courseName(grade.getEnrollment().getCourse().getName())
                .credits(grade.getEnrollment().getCourse().getCredits())
                .semester(grade.getEnrollment().getSemester())
                .midtermScore(grade.getMidtermScore())
                .finalScore(grade.getFinalScore())
                .totalScore(grade.getTotalScore())
                .letterGrade(grade.getLetterGrade())
                .build();
    }

    private Double convertTo4Scale(double score10) {
        if (score10 >= 9.0) return 4.0;
        if (score10 >= 8.5) return 3.7;
        if (score10 >= 8.0) return 3.5;
        if (score10 >= 7.0) return 3.0;
        if (score10 >= 6.5) return 2.5;
        if (score10 >= 5.5) return 2.0;
        if (score10 >= 5.0) return 1.5;
        if (score10 >= 4.0) return 1.0;
        return 0.0;
    }
}
