package com.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL      = "jdbc:mysql://localhost:3306/QuanLySinhVien?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String USER     = "root";       // ← sửa theo máy bạn
    private static final String PASSWORD = "canh123"; // ← sửa theo máy bạn

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}