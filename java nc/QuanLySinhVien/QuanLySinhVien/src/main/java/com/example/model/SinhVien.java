package com.example.model;

import java.time.LocalDate;

public class SinhVien {
    private String maSinhVien;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String nganhHoc; // lưu dạng "CNTT,Toán" nếu 2 ngành

    public SinhVien() {}

    public SinhVien(String maSinhVien, String hoTen,
                    LocalDate ngaySinh, String gioiTinh, String nganhHoc) {
        this.maSinhVien = maSinhVien;
        this.hoTen      = hoTen;
        this.ngaySinh   = ngaySinh;
        this.gioiTinh   = gioiTinh;
        this.nganhHoc   = nganhHoc;
    }

    // ---------- Getters & Setters ----------
    public String getMaSinhVien()             { return maSinhVien; }
    public void   setMaSinhVien(String v)     { this.maSinhVien = v; }

    public String getHoTen()                  { return hoTen; }
    public void   setHoTen(String v)          { this.hoTen = v; }

    public LocalDate getNgaySinh()            { return ngaySinh; }
    public void      setNgaySinh(LocalDate v) { this.ngaySinh = v; }

    public String getGioiTinh()               { return gioiTinh; }
    public void   setGioiTinh(String v)       { this.gioiTinh = v; }

    public String getNganhHoc()               { return nganhHoc; }
    public void   setNganhHoc(String v)       { this.nganhHoc = v; }
}