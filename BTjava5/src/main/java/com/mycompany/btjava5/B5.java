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

// Lớp KhachHang (Quản lý thông tin khách thuê)
class KhachHang {
    private String hoTen, soCMND;
    private int tuoi;
    private String soPhong;
    private int soNgayThue;
    private double giaPhong;

    public KhachHang(String hoTen, int tuoi, String soCMND, String soPhong, int soNgayThue, double giaPhong) {
        this.hoTen = hoTen;
        this.tuoi = tuoi;
        this.soCMND = soCMND;
        this.soPhong = soPhong;
        this.soNgayThue = soNgayThue;
        this.giaPhong = giaPhong;
    }

    public String getSoCMND() { return soCMND; }
    
    public double tinhTien() {
        return soNgayThue * giaPhong;
    }

    @Override
    public String toString() {
        return "Khách: " + hoTen + " - Tuổi: " + tuoi + " - CMND: " + soCMND +
               " - Phòng: " + soPhong + " - Ngày thuê: " + soNgayThue + " - Tổng tiền: " + tinhTien() + " VNĐ";
    }
}

// Lớp Quản lý khách sạn
class QuanLyKhachSan {
    private List<KhachHang> danhSach = new ArrayList<>();

    public void themKhach(KhachHang kh) {
        danhSach.add(kh);
    }

    public void xoaKhach(String soCMND) {
        danhSach.removeIf(kh -> kh.getSoCMND().equals(soCMND));
    }

    public List<KhachHang> getDanhSach() {
        return danhSach;
    }
}

// Giao diện quản lý khách sạn
public class B5 extends JFrame {
    private QuanLyKhachSan qlks = new QuanLyKhachSan();
    private JTextField txtHoTen, txtTuoi, txtCMND, txtSoPhong, txtSoNgayThue, txtGiaPhong, txtTimKiem;
    private JTextArea txtHienThi;

    public B5() {
        setTitle("Quản lý Khách sạn");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField(15);
        add(txtHoTen);

        add(new JLabel("Tuổi:"));
        txtTuoi = new JTextField(5);
        add(txtTuoi);

        add(new JLabel("CMND:"));
        txtCMND = new JTextField(10);
        add(txtCMND);

        add(new JLabel("Số phòng:"));
        txtSoPhong = new JTextField(10);
        add(txtSoPhong);

        add(new JLabel("Số ngày thuê:"));
        txtSoNgayThue = new JTextField(5);
        add(txtSoNgayThue);

        add(new JLabel("Giá phòng/ngày:"));
        txtGiaPhong = new JTextField(10);
        add(txtGiaPhong);

        JButton btnThem = new JButton("Thêm khách");
        JButton btnXoa = new JButton("Xóa khách");
        JButton btnHienThi = new JButton("Hiển thị danh sách");

        add(btnThem);
        add(btnXoa);
        add(btnHienThi);

        txtTimKiem = new JTextField(10);
        add(new JLabel("Tìm CMND:"));
        add(txtTimKiem);

        txtHienThi = new JTextArea(15, 40);
        add(new JScrollPane(txtHienThi));

        btnThem.addActionListener(e -> themKhach());
        btnXoa.addActionListener(e -> xoaKhach());
        btnHienThi.addActionListener(e -> hienThiDanhSach());

        setVisible(true);
    }

    private void themKhach() {
        try {
            String hoTen = txtHoTen.getText();
            int tuoi = Integer.parseInt(txtTuoi.getText());
            String soCMND = txtCMND.getText();
            String soPhong = txtSoPhong.getText();
            int soNgayThue = Integer.parseInt(txtSoNgayThue.getText());
            double giaPhong = Double.parseDouble(txtGiaPhong.getText());

            KhachHang kh = new KhachHang(hoTen, tuoi, soCMND, soPhong, soNgayThue, giaPhong);
            qlks.themKhach(kh);
            JOptionPane.showMessageDialog(this, "Thêm khách thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaKhach() {
        String soCMND = txtTimKiem.getText();
        qlks.xoaKhach(soCMND);
        JOptionPane.showMessageDialog(this, "Đã xóa khách có CMND: " + soCMND);
    }

    private void hienThiDanhSach() {
        txtHienThi.setText("");
        for (KhachHang kh : qlks.getDanhSach()) {
            txtHienThi.append(kh.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new B5();
    }
}

