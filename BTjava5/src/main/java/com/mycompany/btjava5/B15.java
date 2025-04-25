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

// Lớp HopDongThueXe (Lưu thông tin hợp đồng thuê xe)
class HopDongThueXe {
    private String maHD, tenKhach, loaiXe, ngayThue;
    private double giaThue;
    private int soNgayThue;

    public HopDongThueXe(String maHD, String tenKhach, String loaiXe, String ngayThue, double giaThue, int soNgayThue) {
        this.maHD = maHD;
        this.tenKhach = tenKhach;
        this.loaiXe = loaiXe;
        this.ngayThue = ngayThue;
        this.giaThue = giaThue;
        this.soNgayThue = soNgayThue;
    }

    public String getMaHD() { return maHD; }

    public double tinhTongTien() {
        return giaThue * soNgayThue;
    }

    @Override
    public String toString() {
        return "HĐ: " + maHD + " - " + tenKhach + " - Xe: " + loaiXe + " - Ngày thuê: " + ngayThue +
               " - Giá thuê: " + giaThue + " VNĐ/ngày - Tổng tiền: " + tinhTongTien() + " VNĐ";
    }
}

// Lớp quản lý hợp đồng thuê xe
class QuanLyThueXe {
    private List<HopDongThueXe> danhSach = new ArrayList<>();

    public void themHopDong(HopDongThueXe hd) {
        danhSach.add(hd);
    }

    public void xoaHopDong(String maHD) {
        danhSach.removeIf(hd -> hd.getMaHD().equals(maHD));
    }

    public List<HopDongThueXe> getDanhSach() {
        return danhSach;
    }
}

// Giao diện quản lý hợp đồng thuê xe
public class B15 extends JFrame {
    private QuanLyThueXe qlhd = new QuanLyThueXe();
    private JTextField txtMaHD, txtTenKhach, txtLoaiXe, txtNgayThue, txtGiaThue, txtSoNgayThue, txtTimKiem;
    private JTextArea txtHienThi;

    public B15() {
        setTitle("Quản lý Hợp đồng Thuê xe");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Mã HĐ:"));
        txtMaHD = new JTextField(10);
        add(txtMaHD);

        add(new JLabel("Tên khách:"));
        txtTenKhach = new JTextField(15);
        add(txtTenKhach);

        add(new JLabel("Loại xe:"));
        txtLoaiXe = new JTextField(10);
        add(txtLoaiXe);

        add(new JLabel("Ngày thuê:"));
        txtNgayThue = new JTextField(10);
        add(txtNgayThue);

        add(new JLabel("Giá thuê/ngày:"));
        txtGiaThue = new JTextField(10);
        add(txtGiaThue);

        add(new JLabel("Số ngày thuê:"));
        txtSoNgayThue = new JTextField(5);
        add(txtSoNgayThue);

        JButton btnThem = new JButton("Thêm hợp đồng");
        JButton btnXoa = new JButton("Xóa hợp đồng");
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
            String tenKhach = txtTenKhach.getText();
            String loaiXe = txtLoaiXe.getText();
            String ngayThue = txtNgayThue.getText();
            double giaThue = Double.parseDouble(txtGiaThue.getText());
            int soNgayThue = Integer.parseInt(txtSoNgayThue.getText());

            HopDongThueXe hd = new HopDongThueXe(maHD, tenKhach, loaiXe, ngayThue, giaThue, soNgayThue);
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
        for (HopDongThueXe hd : qlhd.getDanhSach()) {
            txtHienThi.append(hd.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new B15();
    }
}
