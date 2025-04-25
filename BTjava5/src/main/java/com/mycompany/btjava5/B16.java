/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.btjava5;

/**
 *
 * @author PC
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Lớp NhanVien (Lưu thông tin nhân viên)
class NhanVien {
    private String maNV, hoTen, chucVu;
    private double luongCoBan, thuong, phat;

    public NhanVien(String maNV, String hoTen, String chucVu, double luongCoBan, double thuong, double phat) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.chucVu = chucVu;
        this.luongCoBan = luongCoBan;
        this.thuong = thuong;
        this.phat = phat;
    }

    public String getMaNV() { return maNV; }

    public double tinhLuongThucNhan() {
        return luongCoBan + thuong - phat;
    }

    @Override
    public String toString() {
        return "NV: " + maNV + " - " + hoTen + " - Chức vụ: " + chucVu +
               " - Lương thực nhận: " + tinhLuongThucNhan() + " VNĐ";
    }
}

// Lớp quản lý lương nhân viên
class QuanLyNhanVien {
    private List<NhanVien> danhSach = new ArrayList<>();

    public void themNhanVien(NhanVien nv) {
        danhSach.add(nv);
    }

    public void xoaNhanVien(String maNV) {
        danhSach.removeIf(nv -> nv.getMaNV().equals(maNV));
    }

    public List<NhanVien> getDanhSach() {
        return danhSach;
    }
}

// Giao diện quản lý lương nhân viên
public class B16 extends JFrame {
    private QuanLyNhanVien qlnv = new QuanLyNhanVien();
    private JTextField txtMaNV, txtHoTen, txtChucVu, txtLuongCoBan, txtThuong, txtPhat, txtTimKiem;
    private JTextArea txtHienThi;

    public B16() {
        setTitle("Quản lý Lương Nhân viên");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Mã NV:"));
        txtMaNV = new JTextField(10);
        add(txtMaNV);

        add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField(15);
        add(txtHoTen);

        add(new JLabel("Chức vụ:"));
        txtChucVu = new JTextField(10);
        add(txtChucVu);

        add(new JLabel("Lương cơ bản:"));
        txtLuongCoBan = new JTextField(10);
        add(txtLuongCoBan);

        add(new JLabel("Thưởng:"));
        txtThuong = new JTextField(10);
        add(txtThuong);

        add(new JLabel("Phạt:"));
        txtPhat = new JTextField(10);
        add(txtPhat);

        JButton btnThem = new JButton("Thêm nhân viên");
        JButton btnXoa = new JButton("Xóa nhân viên");
        JButton btnHienThi = new JButton("Hiển thị danh sách");

        add(btnThem);
        add(btnXoa);
        add(btnHienThi);

        txtTimKiem = new JTextField(10);
        add(new JLabel("Tìm mã NV:"));
        add(txtTimKiem);

        txtHienThi = new JTextArea(15, 40);
        add(new JScrollPane(txtHienThi));

        btnThem.addActionListener(e -> themNhanVien());
        btnXoa.addActionListener(e -> xoaNhanVien());
        btnHienThi.addActionListener(e -> hienThiDanhSach());

        setVisible(true);
    }

    private void themNhanVien() {
        try {
            String maNV = txtMaNV.getText();
            String hoTen = txtHoTen.getText();
            String chucVu = txtChucVu.getText();
            double luongCoBan = Double.parseDouble(txtLuongCoBan.getText());
            double thuong = Double.parseDouble(txtThuong.getText());
            double phat = Double.parseDouble(txtPhat.getText());

            NhanVien nv = new NhanVien(maNV, hoTen, chucVu, luongCoBan, thuong, phat);
            qlnv.themNhanVien(nv);
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaNhanVien() {
        String maNV = txtTimKiem.getText();
        qlnv.xoaNhanVien(maNV);
        JOptionPane.showMessageDialog(this, "Đã xóa nhân viên có mã: " + maNV);
    }

    private void hienThiDanhSach() {
        txtHienThi.setText("");
        for (NhanVien nv : qlnv.getDanhSach()) {
            txtHienThi.append(nv.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new B16();
    }
}
