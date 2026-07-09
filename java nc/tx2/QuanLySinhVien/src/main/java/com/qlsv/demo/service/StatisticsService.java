package com.qlsv.demo.service;

import com.qlsv.demo.dto.statistics.StatisticsResponse;
import com.qlsv.demo.entity.Faculty;
import com.qlsv.demo.repository.FacultyRepository;
import com.qlsv.demo.repository.GradeRepository;
import com.qlsv.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final GradeRepository gradeRepository;

    public StatisticsResponse getStatistics() {
        long totalStudents = studentRepository.countActiveStudents();

        List<Faculty> faculties = facultyRepository.findAllByIsDeletedFalse();
        List<StatisticsResponse.FacultyStudentCount> facultyCounts = faculties.stream()
                .map(f -> StatisticsResponse.FacultyStudentCount.builder()
                        .facultyId(f.getId())
                        .facultyName(f.getName())
                        .studentCount(studentRepository.countByFacultyId(f.getId()))
                        .build())
                .toList();

        long passed = gradeRepository.countPassedGrades();
        long failed = gradeRepository.countFailedGrades();
        long total = gradeRepository.countGradedEntries();
        double passRate = total > 0 ? Math.round((double) passed / total * 10000.0) / 100.0 : 0;
        double failRate = total > 0 ? Math.round((double) failed / total * 10000.0) / 100.0 : 0;

        return StatisticsResponse.builder()
                .totalStudents(totalStudents)
                .studentsByFaculty(facultyCounts)
                .passedCount(passed)
                .failedCount(failed)
                .passRate(passRate)
                .failRate(failRate)
                .build();
    }
}
