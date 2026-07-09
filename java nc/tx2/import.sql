-- 1. Insert Users (Admin & Staff) (Assuming passwords use BCrypt)
-- Note: 'password123' encoded via BCrypt
INSERT INTO users (created_at, updated_at, email, full_name, password, role, username) VALUES 
(NOW(), NOW(), 'admin@example.com', 'System Admin', '$2a$10$fJt5J6EaZqf9Z71d.sKjTuGqkSrxXf47jD.C/A8bL7pCTh0vK6Y5S', 'ROLE_ADMIN', 'admin'),
(NOW(), NOW(), 'staff01@example.com', 'Staff Member 1', '$2a$10$fJt5J6EaZqf9Z71d.sKjTuGqkSrxXf47jD.C/A8bL7pCTh0vK6Y5S', 'ROLE_STAFF', 'staff01');

-- 2. Insert Faculties (Khoa) 
-- (Verified Faculty.java only has: code, name, is_deleted, created_at, updated_at)
INSERT INTO faculties (created_at, updated_at, code, name, is_deleted) VALUES 
(NOW(), NOW(), 'CNTT', 'Công nghệ thông tin', 0),
(NOW(), NOW(), 'KT', 'Kinh tế', 0),
(NOW(), NOW(), 'NN', 'Ngoại ngữ', 0);

-- 3. Insert Majors (Chuyên ngành)
-- (Verified Major.java has: code, name, faculty_id, is_deleted, created_at, updated_at)
INSERT INTO majors (created_at, updated_at, code, name, faculty_id, is_deleted) VALUES 
(NOW(), NOW(), 'KTPM', 'Kỹ thuật phần mềm', 1, 0),
(NOW(), NOW(), 'HTTT', 'Hệ thống thông tin', 1, 0),
(NOW(), NOW(), 'QTTM', 'Quản trị thương mại', 2, 0),
(NOW(), NOW(), 'NNA', 'Ngôn ngữ Anh', 3, 0);

-- 4. Insert Classes (Lớp học)
-- (Verified ClassEntity.java has: code, name, major_id, academic_year, is_deleted, created_at, updated_at)
INSERT INTO classes (created_at, updated_at, code, name, academic_year, major_id, is_deleted) VALUES 
(NOW(), NOW(), 'KTPM01', 'Kỹ thuật phần mềm Khóa 1', 2023, 1, 0),
(NOW(), NOW(), 'KTPM02', 'Kỹ thuật phần mềm Khóa 2', 2023, 1, 0),
(NOW(), NOW(), 'QTTM01', 'Quản trị thương mại 1', 2023, 3, 0);

-- 5. Insert Courses (Học phần)
-- (Verified Course.java has: course_code, name, credits, description, is_deleted, created_at, updated_at)
-- *Note that Course uses "name" natively, not "course_name"*
INSERT INTO courses (created_at, updated_at, course_code, name, credits, description, is_deleted) VALUES 
(NOW(), NOW(), 'JAVA01', 'Lập trình Java cơ bản', 3, 'Học phần cơ bản', 0),
(NOW(), NOW(), 'JAVA02', 'Lập trình Java nâng cao', 3, 'Học phần nâng cao', 0),
(NOW(), NOW(), 'SQL01', 'Cơ sở dữ liệu', 3, 'SQL và chuẩn hóa', 0),
(NOW(), NOW(), 'ENG01', 'Tiếng Anh giao tiếp', 2, 'Tiếng anh căn bản', 0);

-- 6. Insert Students (Sinh viên)
-- (Verified Student.java has: student_code, first_name, last_name, date_of_birth, gender, email, phone, address, status, class_id, is_deleted, created_at, updated_at)
INSERT INTO students (created_at, updated_at, student_code, first_name, last_name, date_of_birth, gender, email, phone, address, status, class_id, is_deleted) VALUES 
(NOW(), NOW(), 'SV001', 'Nguyễn', 'Văn An', '2003-05-15', 'MALE', 'an.nguyen@example.com', '0901234567', 'Hà Nội', 'DANG_HOC', 1, 0),
(NOW(), NOW(), 'SV002', 'Trần', 'Thị Bình', '2004-02-20', 'FEMALE', 'binh.tran@example.com', '0912345678', 'Hải Phòng', 'DANG_HOC', 1, 0),
(NOW(), NOW(), 'SV003', 'Lê', 'Minh Chiến', '2003-10-10', 'MALE', 'chien.le@example.com', '0923456789', 'Đà Nẵng', 'DANG_HOC', 2, 0),
(NOW(), NOW(), 'SV004', 'Phạm', 'Hương Dung', '2002-12-05', 'FEMALE', 'dung.pham@example.com', '0934567890', 'TP HCM', 'DA_TOT_NGHIEP', 3, 0);

-- 7. Insert Enrollments (Đăng ký học phần)
-- (Verified Enrollment.java has: student_id, course_id, semester, enrolled_at, status, created_at)
-- *Note: DOES NOT extend BaseEntity, it uses "enrolled_at" and "created_at" exclusively*
INSERT INTO enrollments (student_id, course_id, semester, enrolled_at, created_at, status) VALUES 
(1, 1, '2023-HK1', NOW(), NOW(), 'REGISTERED'),
(1, 3, '2023-HK1', NOW(), NOW(), 'REGISTERED'),
(2, 1, '2023-HK1', NOW(), NOW(), 'REGISTERED'),
(3, 2, '2023-HK2', NOW(), NOW(), 'REGISTERED');

-- 8. Insert Grades (Điểm số)
-- (Verified Grade.java has: enrollment_id, midterm_score, final_score, total_score, letter_grade, created_at, updated_at)
INSERT INTO grades (enrollment_id, midterm_score, final_score, total_score, letter_grade, created_at, updated_at) VALUES 
(1, 8.5, 9.0, 8.8, 'A', NOW(), NOW()),
(2, 7.0, 8.5, 7.9, 'B', NOW(), NOW()),
(3, 9.0, 9.5, 9.3, 'A+', NOW(), NOW());