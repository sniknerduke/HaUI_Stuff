Xây dựng ứng dụng Spring Boot 3 quản lý sinh viên sử dụng Spring Data JPA + MySQL +
JWT.
Chức năng yêu cầu (BẮT BUỘC PHẢI LÀM)
1. Quản lý Người dùng & Xác thực
• Đăng ký, Đăng nhập (sử dụng JWT)
• Phân quyền: ROLE_ADMIN và ROLE_STAFF
2. Quản lý Sinh viên (Student)
• Thêm mới sinh viên
• Sửa thông tin sinh viên
• Xóa sinh viên (Soft Delete)
• Tìm kiếm sinh viên theo: mã SV, họ tên, email, số điện thoại
• Lọc theo: lớp, khoa, giới tính, tình trạng học tập
• Phân trang danh sách sinh viên
3. Quản lý Lớp học (Class / Faculty / Major)
• Quản lý Khoa (Faculty)
• Quản lý Chuyên ngành (Major)
• Quản lý Lớp (Class)
4. Quản lý Điểm số & Học phần
• Nhập điểm cho sinh viên theo học phần
• Xem bảng điểm của sinh viên
• Tính điểm trung bình tích lũy
5. Quản lý Đăng ký học phần (Enrollment)
• Sinh viên đăng ký học phần
• Admin xem danh sách đăng ký
6. Các yêu cầu kỹ thuật bắt buộc:
• Sử dụng Spring Data JPA + Hibernate
• Thiết kế Database tối thiểu 6–8 bảng
• Sử dụng DTO cho Request và Response
• Validation dữ liệu (@Valid, @NotBlank, @Email, @Positive…)
• Xử lý ngoại lệ toàn cục (GlobalExceptionHandler)
• Trả về Response chuẩn (ResponseEntity)
• Sử dụng Lombok
• Cấu hình application.yml
Điểm cộng (Tăng điểm)
• Upload ảnh sinh viên và lưu trữ
• Sử dụng MapStruct để mapping DTO ↔ Entity
• Swagger UI (springdoc-openapi)
• Export danh sách sinh viên ra file Excel
• Thống kê: Số lượng sinh viên theo khoa, tỷ lệ đậu/rớt
• Audit log (createdAt, updatedAt, createdBy)
• Soft Delete (isDeleted hoặc deletedAt)
Cấu trúc dự án gợi ý
text
com.studentmanagement
 ├── config
 ├── controller
 ├── dto
 ├── entity
 ├── repository
 ├── service
 ├── exception
 └── util
Danh sách Entity gợi ý (Quan trọng)
• User (Admin & Staff)
• Student
• Faculty (Khoa)
• Major (Chuyên ngành)
• Class (Lớp học)
• Course (Học phần)
• Enrollment (Đăng ký học phần)
• Grade (Điểm số)
• StudentImage (ảnh sinh viên)