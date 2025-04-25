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

// Lớp Giáo viên
class GiaoVien {
    private String hoTen, maGV, khoa;
    private double luongCung, luongThuong, tienPhat;

    public GiaoVien(String maGV, String hoTen, String khoa, double luongCung, double luongThuong, double tienPhat) {
        this.maGV = maGV;
        this.hoTen = hoTen;
        this.khoa = khoa;
        this.luongCung = luongCung;
        this.luongThuong = luongThuong;
        this.tienPhat = tienPhat;
    }

    public String getMaGV() { return maGV; }

    public double tinhLuongThucLinh() {
        return luongCung + luongThuong - tienPhat;
    }

    @Override
    public String toString() {
        return "GV: " + maGV + " - " + hoTen + " - Khoa: " + khoa +
               " - Lương: " + tinhLuongThucLinh() + " VNĐ";
    }
}

// Lớp Quản lý giáo viên
class QuanLyGiaoVien {
    private List<GiaoVien> danhSach = new ArrayList<>();

    public void themGiaoVien(GiaoVien gv) {
        danhSach.add(gv);
    }

    public void xoaGiaoVien(String maGV) {
        danhSach.removeIf(gv -> gv.getMaGV().equals(maGV));
    }

    public List<GiaoVien> getDanhSach() {
        return danhSach;
    }
}

// Giao diện quản lý giáo viên
public class B7 extends JFrame {
    private QuanLyGiaoVien qlgv = new QuanLyGiaoVien();
    private JTextField txtMaGV, txtHoTen, txtKhoa, txtLuongCung, txtLuongThuong, txtTienPhat, txtTimKiem;
    private JTextArea txtHienThi;

    public B7() {
        setTitle("Quản lý Giáo viên");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Mã GV:"));
        txtMaGV = new JTextField(10);
        add(txtMaGV);

        add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField(15);
        add(txtHoTen);

        add(new JLabel("Khoa:"));
        txtKhoa = new JTextField(10);
        add(txtKhoa);

        add(new JLabel("Lương cứng:"));
        txtLuongCung = new JTextField(10);
        add(txtLuongCung);

        add(new JLabel("Lương thưởng:"));
        txtLuongThuong = new JTextField(10);
        add(txtLuongThuong);

        add(new JLabel("Tiền phạt:"));
        txtTienPhat = new JTextField(10);
        add(txtTienPhat);

        JButton btnThem = new JButton("Thêm giáo viên");
        JButton btnXoa = new JButton("Xóa giáo viên");
        JButton btnHienThi = new JButton("Hiển thị danh sách");

        add(btnThem);
        add(btnXoa);
        add(btnHienThi);

        txtTimKiem = new JTextField(10);
        add(new JLabel("Tìm mã GV:"));
        add(txtTimKiem);

        txtHienThi = new JTextArea(15, 40);
        add(new JScrollPane(txtHienThi));

        btnThem.addActionListener(e -> themGiaoVien());
        btnXoa.addActionListener(e -> xoaGiaoVien());
        btnHienThi.addActionListener(e -> hienThiDanhSach());

        setVisible(true);
    }

    private void themGiaoVien() {
        try {
            String maGV = txtMaGV.getText();
            String hoTen = txtHoTen.getText();
            String khoa = txtKhoa.getText();
            double luongCung = Double.parseDouble(txtLuongCung.getText());
            double luongThuong = Double.parseDouble(txtLuongThuong.getText());
            double tienPhat = Double.parseDouble(txtTienPhat.getText());

            GiaoVien gv = new GiaoVien(maGV, hoTen, khoa, luongCung, luongThuong, tienPhat);
            qlgv.themGiaoVien(gv);
            JOptionPane.showMessageDialog(this, "Thêm giáo viên thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaGiaoVien() {
        String maGV = txtTimKiem.getText();
        qlgv.xoaGiaoVien(maGV);
        JOptionPane.showMessageDialog(this, "Đã xóa giáo viên có mã: " + maGV);
    }

    private void hienThiDanhSach() {
        txtHienThi.setText("");
        for (GiaoVien gv : qlgv.getDanhSach()) {
            txtHienThi.append(gv.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new B7();
    }
}
