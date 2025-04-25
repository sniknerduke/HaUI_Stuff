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

// Lớp SinhVien
class SinhVien {
    private String maSV, hoTen, lop;
    private double diemToan, diemLy, diemHoa;

    public SinhVien(String maSV, String hoTen, String lop, double diemToan, double diemLy, double diemHoa) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.lop = lop;
        this.diemToan = diemToan;
        this.diemLy = diemLy;
        this.diemHoa = diemHoa;
    }

    public String getMaSV() { return maSV; }

    public double tinhDiemTrungBinh() {
        return (diemToan + diemLy + diemHoa) / 3;
    }

    @Override
    public String toString() {
        return "SV: " + maSV + " - " + hoTen + " - Lớp: " + lop +
               " - ĐTB: " + String.format("%.2f", tinhDiemTrungBinh());
    }
}

// Lớp Quản lý sinh viên
class QuanLySinhVien {
    private List<SinhVien> danhSach = new ArrayList<>();

    public void themSinhVien(SinhVien sv) {
        danhSach.add(sv);
    }

    public void xoaSinhVien(String maSV) {
        danhSach.removeIf(sv -> sv.getMaSV().equals(maSV));
    }

    public List<SinhVien> getDanhSach() {
        return danhSach;
    }
}

// Giao diện quản lý sinh viên
public class B10 extends JFrame {
    private QuanLySinhVien qlsv = new QuanLySinhVien();
    private JTextField txtMaSV, txtHoTen, txtLop, txtToan, txtLy, txtHoa, txtTimKiem;
    private JTextArea txtHienThi;

    public B10() {
        setTitle("Quản lý Sinh viên");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Mã SV:"));
        txtMaSV = new JTextField(10);
        add(txtMaSV);

        add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField(15);
        add(txtHoTen);

        add(new JLabel("Lớp:"));
        txtLop = new JTextField(10);
        add(txtLop);

        add(new JLabel("Điểm Toán:"));
        txtToan = new JTextField(5);
        add(txtToan);

        add(new JLabel("Điểm Lý:"));
        txtLy = new JTextField(5);
        add(txtLy);

        add(new JLabel("Điểm Hóa:"));
        txtHoa = new JTextField(5);
        add(txtHoa);

        JButton btnThem = new JButton("Thêm SV");
        JButton btnXoa = new JButton("Xóa SV");
        JButton btnHienThi = new JButton("Hiển thị danh sách");

        add(btnThem);
        add(btnXoa);
        add(btnHienThi);

        txtTimKiem = new JTextField(10);
        add(new JLabel("Tìm mã SV:"));
        add(txtTimKiem);

        txtHienThi = new JTextArea(15, 40);
        add(new JScrollPane(txtHienThi));

        btnThem.addActionListener(e -> themSinhVien());
        btnXoa.addActionListener(e -> xoaSinhVien());
        btnHienThi.addActionListener(e -> hienThiDanhSach());

        setVisible(true);
    }

    private void themSinhVien() {
        try {
            String maSV = txtMaSV.getText();
            String hoTen = txtHoTen.getText();
            String lop = txtLop.getText();
            double diemToan = Double.parseDouble(txtToan.getText());
            double diemLy = Double.parseDouble(txtLy.getText());
            double diemHoa = Double.parseDouble(txtHoa.getText());

            SinhVien sv = new SinhVien(maSV, hoTen, lop, diemToan, diemLy, diemHoa);
            qlsv.themSinhVien(sv);
            JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaSinhVien() {
        String maSV = txtTimKiem.getText();
        qlsv.xoaSinhVien(maSV);
        JOptionPane.showMessageDialog(this, "Đã xóa sinh viên có mã: " + maSV);
    }

    private void hienThiDanhSach() {
        txtHienThi.setText("");
        for (SinhVien sv : qlsv.getDanhSach()) {
            txtHienThi.append(sv.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new B10();
    }
}
