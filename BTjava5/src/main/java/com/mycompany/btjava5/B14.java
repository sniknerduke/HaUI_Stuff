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

// Lớp DonHang (Lưu thông tin đơn hàng)
class DonHang {
    private String maDH, tenKhach, ngayDat;
    private double tongTien;
    private int soLuong;

    public DonHang(String maDH, String tenKhach, String ngayDat, double tongTien, int soLuong) {
        this.maDH = maDH;
        this.tenKhach = tenKhach;
        this.ngayDat = ngayDat;
        this.tongTien = tongTien;
        this.soLuong = soLuong;
    }

    public String getMaDH() { return maDH; }

    @Override
    public String toString() {
        return "Đơn hàng: " + maDH + " - Khách: " + tenKhach + " - Ngày đặt: " + ngayDat +
               " - Số lượng: " + soLuong + " - Tổng tiền: " + tongTien + " VNĐ";
    }
}

// Lớp quản lý đơn hàng
class QuanLyDonHang {
    private List<DonHang> danhSach = new ArrayList<>();

    public void themDonHang(DonHang dh) {
        danhSach.add(dh);
    }

    public void xoaDonHang(String maDH) {
        danhSach.removeIf(dh -> dh.getMaDH().equals(maDH));
    }

    public List<DonHang> getDanhSach() {
        return danhSach;
    }
}

// Giao diện quản lý đơn hàng
public class B14 extends JFrame {
    private QuanLyDonHang qldh = new QuanLyDonHang();
    private JTextField txtMaDH, txtTenKhach, txtNgayDat, txtTongTien, txtSoLuong, txtTimKiem;
    private JTextArea txtHienThi;

    public B14() {
        setTitle("Quản lý Đơn hàng");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Mã đơn hàng:"));
        txtMaDH = new JTextField(10);
        add(txtMaDH);

        add(new JLabel("Tên khách:"));
        txtTenKhach = new JTextField(15);
        add(txtTenKhach);

        add(new JLabel("Ngày đặt:"));
        txtNgayDat = new JTextField(10);
        add(txtNgayDat);

        add(new JLabel("Tổng tiền:"));
        txtTongTien = new JTextField(10);
        add(txtTongTien);

        add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField(5);
        add(txtSoLuong);

        JButton btnThem = new JButton("Thêm đơn hàng");
        JButton btnXoa = new JButton("Xóa đơn hàng");
        JButton btnHienThi = new JButton("Hiển thị danh sách");

        add(btnThem);
        add(btnXoa);
        add(btnHienThi);

        txtTimKiem = new JTextField(10);
        add(new JLabel("Tìm mã đơn hàng:"));
        add(txtTimKiem);

        txtHienThi = new JTextArea(15, 40);
        add(new JScrollPane(txtHienThi));

        btnThem.addActionListener(e -> themDonHang());
        btnXoa.addActionListener(e -> xoaDonHang());
        btnHienThi.addActionListener(e -> hienThiDanhSach());

        setVisible(true);
    }

    private void themDonHang() {
        try {
            String maDH = txtMaDH.getText();
            String tenKhach = txtTenKhach.getText();
            String ngayDat = txtNgayDat.getText();
            double tongTien = Double.parseDouble(txtTongTien.getText());
            int soLuong = Integer.parseInt(txtSoLuong.getText());

            DonHang dh = new DonHang(maDH, tenKhach, ngayDat, tongTien, soLuong);
            qldh.themDonHang(dh);
            JOptionPane.showMessageDialog(this, "Thêm đơn hàng thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaDonHang() {
        String maDH = txtTimKiem.getText();
        qldh.xoaDonHang(maDH);
        JOptionPane.showMessageDialog(this, "Đã xóa đơn hàng có mã: " + maDH);
    }

    private void hienThiDanhSach() {
        txtHienThi.setText("");
        for (DonHang dh : qldh.getDanhSach()) {
            txtHienThi.append(dh.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new B14();
    }
}
