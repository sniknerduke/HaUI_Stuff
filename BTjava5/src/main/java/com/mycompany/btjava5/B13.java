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

// Lớp HangHoa (Lưu thông tin hàng hóa)
class HangHoa {
    private String maHang, tenHang;
    private double giaNhap;
    private int soLuong;

    public HangHoa(String maHang, String tenHang, double giaNhap, int soLuong) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.giaNhap = giaNhap;
        this.soLuong = soLuong;
    }

    public String getMaHang() { return maHang; }

    public double tinhGiaTriKho() {
        return giaNhap * soLuong;
    }

    @Override
    public String toString() {
        return "Hàng: " + maHang + " - " + tenHang + " - Giá nhập: " + giaNhap + " VNĐ - Số lượng: " + soLuong + " - Tổng giá trị: " + tinhGiaTriKho() + " VNĐ";
    }
}

// Lớp quản lý kho hàng
class QuanLyKho {
    private List<HangHoa> danhSach = new ArrayList<>();

    public void themHang(HangHoa hh) {
        danhSach.add(hh);
    }

    public void xoaHang(String maHang) {
        danhSach.removeIf(hh -> hh.getMaHang().equals(maHang));
    }

    public List<HangHoa> getDanhSach() {
        return danhSach;
    }
}

// Giao diện quản lý kho hàng
public class B13 extends JFrame {
    private QuanLyKho qlkho = new QuanLyKho();
    private JTextField txtMaHang, txtTenHang, txtGiaNhap, txtSoLuong, txtTimKiem;
    private JTextArea txtHienThi;

    public B13() {
        setTitle("Quản lý Kho Hàng");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Mã hàng:"));
        txtMaHang = new JTextField(10);
        add(txtMaHang);

        add(new JLabel("Tên hàng:"));
        txtTenHang = new JTextField(15);
        add(txtTenHang);

        add(new JLabel("Giá nhập:"));
        txtGiaNhap = new JTextField(10);
        add(txtGiaNhap);

        add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField(5);
        add(txtSoLuong);

        JButton btnThem = new JButton("Thêm hàng");
        JButton btnXoa = new JButton("Xóa hàng");
        JButton btnHienThi = new JButton("Hiển thị danh sách");

        add(btnThem);
        add(btnXoa);
        add(btnHienThi);

        txtTimKiem = new JTextField(10);
        add(new JLabel("Tìm mã hàng:"));
        add(txtTimKiem);

        txtHienThi = new JTextArea(15, 40);
        add(new JScrollPane(txtHienThi));

        btnThem.addActionListener(e -> themHang());
        btnXoa.addActionListener(e -> xoaHang());
        btnHienThi.addActionListener(e -> hienThiDanhSach());

        setVisible(true);
    }

    private void themHang() {
        try {
            String maHang = txtMaHang.getText();
            String tenHang = txtTenHang.getText();
            double giaNhap = Double.parseDouble(txtGiaNhap.getText());
            int soLuong = Integer.parseInt(txtSoLuong.getText());

            HangHoa hh = new HangHoa(maHang, tenHang, giaNhap, soLuong);
            qlkho.themHang(hh);
            JOptionPane.showMessageDialog(this, "Thêm hàng thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaHang() {
        String maHang = txtTimKiem.getText();
        qlkho.xoaHang(maHang);
        JOptionPane.showMessageDialog(this, "Đã xóa hàng có mã: " + maHang);
    }

    private void hienThiDanhSach() {
        txtHienThi.setText("");
        for (HangHoa hh : qlkho.getDanhSach()) {
            txtHienThi.append(hh.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new B13();
    }
}

