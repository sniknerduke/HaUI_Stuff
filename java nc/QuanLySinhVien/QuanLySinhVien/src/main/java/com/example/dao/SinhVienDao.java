package com.example.dao;

import com.example.db.DatabaseConnection;
import com.example.model.SinhVien;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SinhVienDao {

    // ---------- Lấy 1 sinh viên theo mã ----------
    public SinhVien get(String maSinhVien) {
        String sql = "SELECT * FROM SinhVien WHERE MaSinhVien = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSinhVien);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------- Lấy tất cả sinh viên ----------
    public List<SinhVien> getAll() {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM SinhVien";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ---------- Thêm sinh viên ----------
    public boolean insert(SinhVien sv) {
        String sql = "INSERT INTO SinhVien VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sv.getMaSinhVien());
            ps.setString(2, sv.getHoTen());
            ps.setDate  (3, Date.valueOf(sv.getNgaySinh()));
            ps.setString(4, sv.getGioiTinh());
            ps.setString(5, sv.getNganhHoc());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------- Cập nhật sinh viên ----------
    public boolean update(SinhVien sv) {
        String sql = "UPDATE SinhVien SET HoTen=?, NgaySinh=?, GioiTinh=?, NganhHoc=? WHERE MaSinhVien=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sv.getHoTen());
            ps.setDate  (2, Date.valueOf(sv.getNgaySinh()));
            ps.setString(3, sv.getGioiTinh());
            ps.setString(4, sv.getNganhHoc());
            ps.setString(5, sv.getMaSinhVien());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------- Xóa sinh viên ----------
    public boolean delete(String maSinhVien) {
        String sql = "DELETE FROM SinhVien WHERE MaSinhVien = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSinhVien);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------- Helper: map ResultSet → SinhVien ----------
    private SinhVien mapRow(ResultSet rs) throws SQLException {
        return new SinhVien(
                rs.getString("MaSinhVien"),
                rs.getString("HoTen"),
                rs.getDate("NgaySinh").toLocalDate(),
                rs.getString("GioiTinh"),
                rs.getString("NganhHoc")
        );
    }
}