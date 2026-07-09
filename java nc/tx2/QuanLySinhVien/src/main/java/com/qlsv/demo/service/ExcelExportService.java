package com.qlsv.demo.service;

import com.qlsv.demo.entity.Student;
import com.qlsv.demo.repository.StudentRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelExportService {

    private final StudentRepository studentRepository;

    public ByteArrayInputStream exportStudents() throws IOException {
        Specification<Student> spec = (root, query, cb) -> cb.equal(root.get("isDeleted"), false);
        List<Student> students = studentRepository.findAll(spec);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Danh sách sinh viên");

            // Header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Header row
            String[] headers = {"STT", "Mã SV", "Họ", "Tên", "Ngày sinh", "Giới tính",
                    "Email", "SĐT", "Lớp", "Chuyên ngành", "Khoa", "Trạng thái"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            int rowIdx = 1;
            for (Student s : students) {
                Row row = sheet.createRow(rowIdx);
                row.createCell(0).setCellValue(rowIdx);
                row.createCell(1).setCellValue(s.getStudentCode());
                row.createCell(2).setCellValue(s.getLastName());
                row.createCell(3).setCellValue(s.getFirstName());
                row.createCell(4).setCellValue(s.getDateOfBirth() != null ? s.getDateOfBirth().toString() : "");
                row.createCell(5).setCellValue(s.getGender() != null ? s.getGender().name() : "");
                row.createCell(6).setCellValue(s.getEmail() != null ? s.getEmail() : "");
                row.createCell(7).setCellValue(s.getPhone() != null ? s.getPhone() : "");
                row.createCell(8).setCellValue(s.getClassEntity() != null ? s.getClassEntity().getName() : "");
                row.createCell(9).setCellValue(s.getClassEntity() != null && s.getClassEntity().getMajor() != null
                        ? s.getClassEntity().getMajor().getName() : "");
                row.createCell(10).setCellValue(s.getClassEntity() != null && s.getClassEntity().getMajor() != null
                        && s.getClassEntity().getMajor().getFaculty() != null
                        ? s.getClassEntity().getMajor().getFaculty().getName() : "");
                row.createCell(11).setCellValue(s.getStatus() != null ? s.getStatus().name() : "");
                rowIdx++;
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
