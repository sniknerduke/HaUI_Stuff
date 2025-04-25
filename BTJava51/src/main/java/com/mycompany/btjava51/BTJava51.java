/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.btjava51;

/**
 *
 * @author PC
 */
import java.time.LocalDate;
import java.util.ArrayList;

// Lớp trừu tượng HangHoa
abstract class HangHoa {
    protected String maHang;
    protected String tenHang;
    protected int soLuongTon;
    protected double donGia;

    public HangHoa(String maHang, String tenHang, int soLuongTon, double donGia) {
        if (maHang == null || maHang.isEmpty()) throw new IllegalArgumentException("Mã hàng không được rỗng");
        if (tenHang == null || tenHang.isEmpty()) throw new IllegalArgumentException("Tên hàng không được rỗng");
        if (soLuongTon < 0) throw new IllegalArgumentException("Số lượng tồn phải >= 0");
        if (donGia <= 0) throw new IllegalArgumentException("Đơn giá phải > 0");
        
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.soLuongTon = soLuongTon;
        this.donGia = donGia;
    }

    public abstract double tinhVAT();
    public abstract String danhGiaMucDoBanBuon();
    
    @Override
    public String toString() {
        return "Mã: " + maHang + ", Tên: " + tenHang + ", Số lượng: " + soLuongTon + ", Đơn giá: " + donGia;
    }
}

class HangThucPham extends HangHoa {
    private LocalDate ngaySanXuat, ngayHetHan;
    private String nhaCungCap;

    public HangThucPham(String maHang, String tenHang, int soLuongTon, double donGia, LocalDate ngaySanXuat, LocalDate ngayHetHan, String nhaCungCap) {
        super(maHang, tenHang, soLuongTon, donGia);
        if (ngayHetHan.isBefore(ngaySanXuat)) throw new IllegalArgumentException("Ngày hết hạn phải sau ngày sản xuất");
        this.ngaySanXuat = ngaySanXuat;
        this.ngayHetHan = ngayHetHan;
        this.nhaCungCap = nhaCungCap;
    }

    @Override
    public double tinhVAT() {
        return 0.05 * donGia;
    }

    @Override
    public String danhGiaMucDoBanBuon() {
        return (soLuongTon > 0 && ngayHetHan.isBefore(LocalDate.now())) ? "Khó bán" : "Không đánh giá";
    }
}

class HangDienMay extends HangHoa {
    private int thoiGianBaoHanh;
    private double congSuat;

    public HangDienMay(String maHang, String tenHang, int soLuongTon, double donGia, int thoiGianBaoHanh, double congSuat) {
        super(maHang, tenHang, soLuongTon, donGia);
        if (thoiGianBaoHanh < 0 || congSuat <= 0) throw new IllegalArgumentException("Giá trị không hợp lệ");
        this.thoiGianBaoHanh = thoiGianBaoHanh;
        this.congSuat = congSuat;
    }

    @Override
    public double tinhVAT() {
        return 0.10 * donGia;
    }

    @Override
    public String danhGiaMucDoBanBuon() {
        return (soLuongTon < 3) ? "Bán được" : "Không đánh giá";
    }
}

class HangSanhSu extends HangHoa {
    private String nhaSanXuat;
    private LocalDate ngayNhapKho;

    public HangSanhSu(String maHang, String tenHang, int soLuongTon, double donGia, String nhaSanXuat, LocalDate ngayNhapKho) {
        super(maHang, tenHang, soLuongTon, donGia);
        this.nhaSanXuat = nhaSanXuat;
        this.ngayNhapKho = ngayNhapKho;
    }

    @Override
    public double tinhVAT() {
        return 0.10 * donGia;
    }

    @Override
    public String danhGiaMucDoBanBuon() {
        return (soLuongTon > 50 && ngayNhapKho.plusDays(10).isBefore(LocalDate.now())) ? "Bán chậm" : "Không đánh giá";
    }
}

class QuanLyKho {
    private ArrayList<HangHoa> danhSach = new ArrayList<>();
    
    public void themHangHoa(HangHoa hang) {
        for (HangHoa h : danhSach) {
            if (h.maHang.equals(hang.maHang)) {
                System.out.println("Mã hàng đã tồn tại!");
                return;
            }
        }
        danhSach.add(hang);
    }
    
    public void inDanhSach() {
        for (HangHoa hang : danhSach) {
            System.out.println(hang);
        }
    }
}

public class BTJava51 {
    public static void main(String[] args) {
        QuanLyKho kho = new QuanLyKho();
        kho.themHangHoa(new HangDienMay("DM01", "Tủ lạnh", 2, 5000000, 24, 1.5));
        kho.themHangHoa(new HangThucPham("TP01", "Sữa tươi", 10, 30000, LocalDate.of(2025, 3, 1), LocalDate.of(2025, 6, 1), "Vinamilk"));
        kho.themHangHoa(new HangSanhSu("SS01", "Bát sứ", 60, 150000, "Bát Tràng", LocalDate.of(2025, 3, 10)));
        
        kho.inDanhSach();
    }
}
