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

// Lớp Ngoc (Lưu thông tin ngọc quý)
class Ngoc {
    private String maNgoc, tenNgoc;
    private double giaBan;
    private int soLuong;

    public Ngoc(String maNgoc, String tenNgoc, double giaBan, int soLuong) {
        this.maNgoc = maNgoc;
        this.tenNgoc = tenNgoc;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
    }

    public String getMaNgoc() { return maNgoc; }

    public double tinhTongTien() {
        return giaBan * soLuong;
    }

    @Override
    public String toString() {
        return "Ngọc: " + maNgoc + " - " + tenNgoc + " - Giá: " + giaBan + " VNĐ - Số lượng: " + soLuong + " - Tổng tiền: " + tinhTongTien() + " VNĐ";
    }
}

// Lớp quản lý cửa hàng ngọc
class QuanLyNgoc {
    private List<Ngoc> danhSach = new ArrayList<>();

    public void themNgoc(Ngoc n) {
        danhSach.add(n);
    }

    public void xoaNgoc(String maNgoc) {
        danhSach.removeIf(n -> n.getMaNgoc().equals(maNgoc));
    }

    public List<Ngoc> getDanhSach() {
        return danhSach;
    }
}

// Giao diện quản lý cửa hàng ngọc
public class B12 extends JFrame {
    private QuanLyNgoc qlngoc = new QuanLyNgoc();
    private JTextField txtMaNgoc, txtTenNgoc, txtGiaBan, txtSoLuong, txtTimKiem;
    private JTextArea txtHienThi;

    public B12() {
        setTitle("Quản lý Cửa hàng Ngọc");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Mã ngọc:"));
        txtMaNgoc = new JTextField(10);
        add(txtMaNgoc);

        add(new JLabel("Tên ngọc:"));
        txtTenNgoc = new JTextField(15);
        add(txtTenNgoc);

        add(new JLabel("Giá bán:"));
        txtGiaBan = new JTextField(10);
        add(txtGiaBan);

        add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField(5);
        add(txtSoLuong);

        JButton btnThem = new JButton("Thêm ngọc");
        JButton btnXoa = new JButton("Xóa ngọc");
        JButton btnHienThi = new JButton("Hiển thị danh sách");

        add(btnThem);
        add(btnXoa);
        add(btnHienThi);

        txtTimKiem = new JTextField(10);
        add(new JLabel("Tìm mã ngọc:"));
        add(txtTimKiem);

        txtHienThi = new JTextArea(15, 40);
        add(new JScrollPane(txtHienThi));

        btnThem.addActionListener(e -> themNgoc());
        btnXoa.addActionListener(e -> xoaNgoc());
        btnHienThi.addActionListener(e -> hienThiDanhSach());

        setVisible(true);
    }

    private void themNgoc() {
        try {
            String maNgoc = txtMaNgoc.getText();
            String tenNgoc = txtTenNgoc.getText();
            double giaBan = Double.parseDouble(txtGiaBan.getText());
            int soLuong = Integer.parseInt(txtSoLuong.getText());

            Ngoc n = new Ngoc(maNgoc, tenNgoc, giaBan, soLuong);
            qlngoc.themNgoc(n);
            JOptionPane.showMessageDialog(this, "Thêm ngọc thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaNgoc() {
        String maNgoc = txtTimKiem.getText();
        qlngoc.xoaNgoc(maNgoc);
        JOptionPane.showMessageDialog(this, "Đã xóa ngọc có mã: " + maNgoc);
    }

    private void hienThiDanhSach() {
        txtHienThi.setText("");
        for (Ngoc n : qlngoc.getDanhSach()) {
            txtHienThi.append(n.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new B12();
    }
}
