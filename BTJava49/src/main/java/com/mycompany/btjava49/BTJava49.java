/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.btjava49;

/**
 *
 * @author PC
 */
import java.util.*;

// Lop cha HoaDonTienDien
class HoaDonTienDien {
    protected String maKhachHang;
    protected String hoTen;
    protected int ngay, thang, nam;
    protected double soLuong;
    protected double donGia;

    public HoaDonTienDien(String maKhachHang, String hoTen, int ngay, int thang, int nam, double soLuong, double donGia) {
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.ngay = ngay;
        this.thang = thang;
        this.nam = nam;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public double tinhThanhTien() {
        return soLuong * donGia;
    }

    public int getThang() {
        return thang;
    }

    public int getNam() {
        return nam;
    }
}

// Lop con KhachHangVietNam
class KhachHangVietNam extends HoaDonTienDien {
    private String doiTuongKhachHang;
    private double dinhMuc;

    public KhachHangVietNam(String maKhachHang, String hoTen, int ngay, int thang, int nam, double soLuong, double donGia, String doiTuongKhachHang, double dinhMuc) {
        super(maKhachHang, hoTen, ngay, thang, nam, soLuong, donGia);
        this.doiTuongKhachHang = doiTuongKhachHang;
        this.dinhMuc = dinhMuc;
    }

    @Override
    public double tinhThanhTien() {
        if (soLuong <= dinhMuc) {
            return soLuong * donGia;
        } else {
            return dinhMuc * donGia + (soLuong - dinhMuc) * donGia * 2.5;
        }
    }
}

// Lop con KhachHangNuocNgoai
class KhachHangNuocNgoai extends HoaDonTienDien {
    private String quocTich;

    public KhachHangNuocNgoai(String maKhachHang, String hoTen, int ngay, int thang, int nam, double soLuong, double donGia, String quocTich) {
        super(maKhachHang, hoTen, ngay, thang, nam, soLuong, donGia);
        this.quocTich = quocTich;
    }
}

// Lop quan ly danh sach hoa don
class QuanLyHoaDon {
    private List<HoaDonTienDien> danhSachHoaDon = new ArrayList<>();

    public void themHoaDon(HoaDonTienDien hoaDon) {
        danhSachHoaDon.add(hoaDon);
    }

    public double tongSoLuongTheoLoai(Class<?> loaiKhachHang) {
        double tong = 0;
        for (HoaDonTienDien hoaDon : danhSachHoaDon) {
            if (loaiKhachHang.isInstance(hoaDon)) {
                tong += hoaDon.soLuong;
            }
        }
        return tong;
    }

    public double trungBinhThanhTienKhachNuocNgoai() {
        double tongTien = 0;
        int count = 0;
        for (HoaDonTienDien hoaDon : danhSachHoaDon) {
            if (hoaDon instanceof KhachHangNuocNgoai) {
                tongTien += hoaDon.tinhThanhTien();
                count++;
            }
        }
        return count == 0 ? 0 : tongTien / count;
    }

    public void xuatHoaDonThang9Nam2013() {
        for (HoaDonTienDien hoaDon : danhSachHoaDon) {
            if (hoaDon.getThang() == 9 && hoaDon.getNam() == 2013) {
                System.out.println("Ma KH: " + hoaDon.maKhachHang + " - Thanh tien: " + hoaDon.tinhThanhTien());
            }
        }
    }
}

// Lop main de chay chuong trinh
public class BTJava49 {
    public static void main(String[] args) {
        QuanLyHoaDon qlhd = new QuanLyHoaDon();

        qlhd.themHoaDon(new KhachHangVietNam("VN01", "Nguyen Van A", 10, 9, 2013, 100, 2000, "Sinh hoat", 50));
        qlhd.themHoaDon(new KhachHangNuocNgoai("NN01", "John Smith", 15, 9, 2013, 200, 2500, "USA"));
        qlhd.themHoaDon(new KhachHangVietNam("VN02", "Le Thi B", 5, 10, 2013, 150, 2000, "Kinh doanh", 100));

        System.out.println("Tong so luong cua khach hang Viet Nam: " + qlhd.tongSoLuongTheoLoai(KhachHangVietNam.class));
        System.out.println("Tong so luong cua khach hang nuoc ngoai: " + qlhd.tongSoLuongTheoLoai(KhachHangNuocNgoai.class));
        System.out.println("Trung binh thanh tien khach hang nuoc ngoai: " + qlhd.trungBinhThanhTienKhachNuocNgoai());
        
        System.out.println("Danh sach hoa don thang 9 nam 2013:");
        qlhd.xuatHoaDonThang9Nam2013();
    }
}
