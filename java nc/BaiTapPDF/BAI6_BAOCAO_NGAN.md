# BAI 6 - KET NOI CO SO DU LIEU (IT6020)

## 1) Noi dung da thuc hien
- 6.1 Hibernate project: tao moi project tai BaiTapPDF/HibernateProject.
- 6.2 JPA project: tao moi project tai BaiTapPDF/JPAProject (ObjectDB).
- 6.3 Script test truy cap CSDL module User: cap nhat project UserManagement theo dung mau main.
- 6.4 Kich ban kiem thu JMeter: tao file BaiTapPDF/JMeter/user-module-test-plan.jmx.

## 2) Mo ta ky thuat SQL da su dung
- INSERT co tham so (PreparedStatement) de tranh SQL Injection.
- SELECT danh sach co phan trang bang LIMIT at,total.
- COUNT ban ghi bang ham tong hop COUNT(*).
- Quan ly giao dich SQL bang begin/commit/rollback de giu tinh nhat quan.
- Dung truy van co dieu kien user_deleted de loc du lieu chua bi xoa logic.

## 3) Cac file/chinh sua chinh
- UserManagement/src/main/Main.java
- UserManagement/src/objects/UserObject.java
- UserManagement/src/cms/user/UserFunctionImpl.java
- BaiTapPDF/HibernateProject/*
- BaiTapPDF/JPAProject/*
- BaiTapPDF/JMeter/user-module-test-plan.jmx

## 4) Cach chay nhanh
### 4.1 Chay UserManagement (JDBC)
1. Tao CSDL bang script UserManagement/database.sql.
2. Kiem tra thong tin ket noi trong UserManagement/src/util/ConnectionPoolImpl.java.
3. Chay class main.Main.

### 4.2 Chay Hibernate project
1. Vao thu muc BaiTapPDF/HibernateProject.
2. Chinh sua thong tin DB trong src/main/resources/hibernate.cfg.xml neu can.
3. Build: mvn -DskipTests package
4. Chay class haui.fit.HibernateExample1.

### 4.3 Chay JPA project
1. Vao thu muc BaiTapPDF/JPAProject.
2. Build: mvn -DskipTests package
3. Chay class haui.fit.jpa.JPAUserExample.

### 4.4 Chay JMeter
1. Mo Apache JMeter.
2. Open file BaiTapPDF/JMeter/user-module-test-plan.jmx.
3. Them JDBC driver MySQL vao JMeter lib neu chua co.
4. Run test va theo doi Summary Report.
