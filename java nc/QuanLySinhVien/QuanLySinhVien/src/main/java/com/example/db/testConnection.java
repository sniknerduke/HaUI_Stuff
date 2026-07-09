package com.example.db;

public class testConnection {
    public static void main(String[] args) {
        try {
            var conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println("Kết nối MySQL thành công rồi nhé Cảnh!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
