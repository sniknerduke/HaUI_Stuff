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

// Lớp HopDongLaoDong (Lưu thông tin hợp đồng)
class HopDongLaoDong {
    private String maHD, tenNV, ngayKy;
    private double heSoLuong, luongCoBan;

    public HopDongLaoDong(String maHD, String tenNV, String ngayKy, double heSoLuong, double luongCoBan) {
        this.maHD = maHD;
        this.tenNV = tenNV;
        this.ngayKy = ngayKy;
        this.heSoLuong = heSoLuong;
        this.luongCoBan = luongCoBan;
    }

    public String getMaHD() { return maHD; }

    public double tinhLuong() {
        return heSoLuong * luongCoBan;
    }

    @Override
    public String toString() {
        return "HĐ: " + maHD + " - " + tenNV + " - Ngày ký: " + ngayKy +
               " - HSL: " + heSoLuong + " - Lương: " + tinhLuong() + " VNĐ";
    }
}

// Lớp quản lý hợp đồng lao động
class QuanLyHopDong {
    private List<HopDongLaoDong> danhSach = new ArrayList<>();

    public void themHopDong(HopDongLaoDong hd) {
        danhSach.add(hd);
    }

    public void xoaHopDong(String maHD) {
        danhSach.removeIf(hd -> hd.getMaHD().equals(maHD));
    }

    public List<HopDongLaoDong> getDanhSach() {
        return danhSach;
    }
}

// Giao diện quản lý hợp đồng lao động
public class B11 extends JFrame {
    private QuanLyHopDong qlhd = new QuanLyHopDong();
    private JTextField txtMaHD, txtTenNV, txtNgayKy, txtHeSoLuong, txtLuongCoBan, txtTimKiem;
    private JTextArea txtHienThi;

    public B11() {
        setTitle("Quản lý Hợp đồng Lao động");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Mã HĐ:"));
        txtMaHD = new JTextField(10);
        add(txtMaHD);

        add(new JLabel("Tên NV:"));
        txtTenNV = new JTextField(15);
        add(txtTenNV);

        add(new JLabel("Ngày ký:"));
        txtNgayKy = new JTextField(10);
        add(txtNgayKy);

        add(new JLabel("Hệ số lương:"));
        txtHeSoLuong = new JTextField(5);
        add(txtHeSoLuong);

        add(new JLabel("Lương cơ bản:"));
        txtLuongCoBan = new JTextField(10);
        add(txtLuongCoBan);

        JButton btnThem = new JButton("Thêm HĐ");
        JButton btnXoa = new JButton("Xóa HĐ");
        JButton btnHienThi = new JButton("Hiển thị danh sách");

        add(btnThem);
        add(btnXoa);
        add(btnHienThi);

        txtTimKiem = new JTextField(10);
        add(new JLabel("Tìm mã HĐ:"));
        add(txtTimKiem);

        txtHienThi = new JTextArea(15, 40);
        add(new JScrollPane(txtHienThi));

        btnThem.addActionListener(e -> themHopDong());
        btnXoa.addActionListener(e -> xoaHopDong());
        btnHienThi.addActionListener(e -> hienThiDanhSach());

        setVisible(true);
    }

    private void themHopDong() {
        try {
            String maHD = txtMaHD.getText();
            String tenNV = txtTenNV.getText();
            String ngayKy = txtNgayKy.getText();
            double heSoLuong = Double.parseDouble(txtHeSoLuong.getText());
            double luongCoBan = Double.parseDouble(txtLuongCoBan.getText());

            HopDongLaoDong hd = new HopDongLaoDong(maHD, tenNV, ngayKy, heSoLuong, luongCoBan);
            qlhd.themHopDong(hd);
            JOptionPane.showMessageDialog(this, "Thêm hợp đồng thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaHopDong() {
        String maHD = txtTimKiem.getText();
        qlhd.xoaHopDong(maHD);
        JOptionPane.showMessageDialog(this, "Đã xóa hợp đồng có mã: " + maHD);
    }

    private void hienThiDanhSach() {
        txtHienThi.setText("");
        for (HopDongLaoDong hd : qlhd.getDanhSach()) {
            txtHienThi.append(hd.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new B11();
    }
}
