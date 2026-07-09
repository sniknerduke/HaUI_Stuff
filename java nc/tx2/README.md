# Student Management System (Hệ thống Quản lý Sinh viên)

Dự án quản lý sinh viên được xây dựng bằng **Spring Boot 3**, sử dụng **JPA**, **MySQL**, và bảo mật với **JWT (JSON Web Token)**. Dự án cung cấp các API để quản lý sinh viên, giảng viên, lớp học, khóa học, đăng ký môn học và điểm số.

## 🚀 Công nghệ sử dụng

- **Backend:** Java 24, Spring Boot 3.5.14
- **Security:** Spring Security, JWT (jjwt)
- **Database:** MySQL
- **ORM:** Spring Data JPA, Hibernate
- **Mapping:** MapStruct
- **Documentation:** Swagger/OpenAPI (SpringDoc)
- **Frontend:** HTML/CSS/JavaScript cơ bản (nằm trong thư mục `frontend/`)

## 📋 Tính năng chính

- **Xác thực & Phân quyền:** Đăng nhập, đăng ký và bảo mật API bằng JWT.
- **Quản lý Sinh viên:** Thêm, sửa, xóa, tra cứu thông tin sinh viên và ảnh đại diện.
- **Quản lý Đào tạo:** Quản lý Khoa (Faculty), Ngành (Major), Lớp học (Class), Khóa học (Course).
- **Quản lý Học tập:** Đăng ký môn học (Enrollment) và nhập điểm (Grade).
- **Thống kê:** Cung cấp các API thống kê dữ liệu.
- **Tài liệu API:** Tích hợp Swagger UI để dễ dàng thử nghiệm API.

## 🛠 Cấu hình & Cài đặt

### Yêu cầu hệ thống
- Java 24 trở lên
- MySQL Server
- Maven

### Các bước cài đặt

1.  **Clone repository**
2.  **Cấu hình Database:**
    - Mở file [src/main/resources/application.yaml](src/main/resources/application.yaml).
    - Cập nhật thông tin `username` và `password` của MySQL.
    - Mặc định cơ sở dữ liệu sẽ tự tạo với tên `student_management`.
3.  **Chạy ứng dụng:**
    ```bash
    ./mvnw spring-boot:run
    ```

## ⚒ Hướng dẫn sử dụng

### 1. Sử dụng Giao diện (Frontend)
- Truy cập vào thư mục `frontend/` và mở file `index.html` trong trình duyệt.
- Bạn cần **Đăng ký** hoặc **Đăng nhập** để bắt đầu sử dụng.
- Sau khi đăng nhập, token sẽ được lưu vào trình duyệt, cho phép bạn quản lý Sinh viên, Khoa, Ngành và các dữ liệu khác.

### 2. Sử dụng API (Postman / Swagger)
- **Đăng nhập:** Truy cập POST `http://localhost:8080/api/auth/login` với body:
  ```json
  {
    "username": "tên_đăng_nhập",
    "password": "mật_khẩu"
  }
  ```
- **Xác thực:** Lấy `accessToken` từ kết quả đăng nhập và thêm vào Header của các request sau:
  `Authorization: Bearer <token>`
- **Thử nghiệm:** Sử dụng [Swagger UI](http://localhost:8080/swagger-ui.html) để xem chi tiết các đầu cuối (endpoints) và gửi test request trực tiếp.

## 📖 Tài liệu API

Sau khi chạy ứng dụng, bạn có thể truy cập tài liệu API tại:
- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **API Docs:** [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

Dự án cũng đi kèm tệp [Student_API.postman_collection.json](Student_API.postman_collection.json) để bạn có thể import vào Postman.

## 📂 Cấu trúc thư mục chính

- `src/main/java/com/qlsv/demo/controller`: Các lớp xử lý API.
- `src/main/java/com/qlsv/demo/entity`: Định nghĩa các thực thể database.
- `src/main/java/com/qlsv/demo/service`: Chứa logic nghiệp vụ.
- `src/main/java/com/qlsv/demo/repository`: Giao tiếp với cơ sở dữ liệu.
- `src/main/java/com/qlsv/demo/dto`: Các đối tượng chuyển đổi dữ liệu.
- `frontend/`: Giao diện người dùng đơn giản.

## 📝 Dữ liệu mẫu
Bạn có thể tìm thấy dữ liệu khởi tạo trong file [src/import.sql](src/import.sql) để kiểm tra các tính năng của hệ thống.
