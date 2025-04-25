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

// Lớp cha GiaoDich
class GiaoDich {
    protected String maGiaoDich, ngayGiaoDich;
    protected double donGia;
    protected int soLuong;

    public GiaoDich(String maGiaoDich, String ngayGiaoDich, double donGia, int soLuong) {
        this.maGiaoDich = maGiaoDich;
        this.ngayGiaoDich = ngayGiaoDich;
        this.donGia = donGia;
        this.soLuong = soLuong;
    }

    public String getMaGiaoDich() { return maGiaoDich; }

    public double tinhThanhTien() {
        return donGia * soLuong;
    }

    @Override
    public String toString() {
        return maGiaoDich + " - Ngày: " + ngayGiaoDich + " - Đơn giá: " + donGia + 
               " - Số lượng: " + soLuong + " - Thành tiền: " + tinhThanhTien() + " VNĐ";
    }
}

// Lớp Giao dịch Vàng kế thừa từ GiaoDich
class GiaoDichVang extends GiaoDich {
    private String loaiVang;

    public GiaoDichVang(String maGiaoDich, String ngayGiaoDich, double donGia, int soLuong, String loaiVang) {
        super(maGiaoDich, ngayGiaoDich, donGia, soLuong);
        this.loaiVang = loaiVang;
    }

    @Override
    public String toString() {
        return "GD Vàng: " + super.toString() + " - Loại vàng: " + loaiVang;
    }
}

// Lớp Giao dịch Tiền tệ kế thừa từ GiaoDich
class GiaoDichTienTe extends GiaoDich {
    private double tiGia;
    private String loaiTien;

    public GiaoDichTienTe(String maGiaoDich, String ngayGiaoDich, double donGia, int soLuong, String loaiTien, double tiGia) {
        super(maGiaoDich, ngayGiaoDich, donGia, soLuong);
        this.loaiTien = loaiTien;
        this.tiGia = tiGia;
    }

    @Override
    public double tinhThanhTien() {
        return loaiTien.equalsIgnoreCase("VND") ? super.tinhThanhTien() : super.tinhThanhTien() * tiGia;
    }

    @Override
    public String toString() {
        return "GD Tiền tệ: " + super.toString() + " - Loại tiền: " + loaiTien + " - Tỷ giá: " + tiGia;
    }
}

// Lớp quản lý giao dịch
class QuanLyGiaoDich {
    private List<GiaoDich> danhSach = new ArrayList<>();

    public void themGiaoDich(GiaoDich gd) {
        danhSach.add(gd);
    }

    public void xoaGiaoDich(String maGiaoDich) {
        danhSach.removeIf(gd -> gd.getMaGiaoDich().equals(maGiaoDich));
    }

    public List<GiaoDich> getDanhSach() {
        return danhSach;
    }
}

// Giao diện quản lý giao dịch
public class B8 extends JFrame {
    private QuanLyGiaoDich qlgd = new QuanLyGiaoDich();
    private JTextField txtMaGD, txtNgayGD, txtDonGia, txtSoLuong, txtLoaiVang, txtLoaiTien, txtTiGia, txtTimKiem;
    private JTextArea txtHienThi;
    private JComboBox<String> cboLoaiGD;

    public B8() {
        setTitle("Quản lý Giao dịch");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Loại GD:"));
        cboLoaiGD = new JComboBox<>(new String[]{"Vàng", "Tiền tệ"});
        add(cboLoaiGD);

        add(new JLabel("Mã GD:"));
        txtMaGD = new JTextField(10);
        add(txtMaGD);

        add(new JLabel("Ngày GD:"));
        txtNgayGD = new JTextField(10);
        add(txtNgayGD);

        add(new JLabel("Đơn giá:"));
        txtDonGia = new JTextField(10);
        add(txtDonGia);

        add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField(5);
        add(txtSoLuong);

        add(new JLabel("Loại vàng:"));
        txtLoaiVang = new JTextField(10);
        add(txtLoaiVang);

        add(new JLabel("Loại tiền:"));
        txtLoaiTien = new JTextField(10);
        add(txtLoaiTien);

        add(new JLabel("Tỷ giá:"));
        txtTiGia = new JTextField(10);
        add(txtTiGia);

        JButton btnThem = new JButton("Thêm giao dịch");
        JButton btnXoa = new JButton("Xóa giao dịch");
        JButton btnHienThi = new JButton("Hiển thị danh sách");

        add(btnThem);
        add(btnXoa);
        add(btnHienThi);

        txtTimKiem = new JTextField(10);
        add(new JLabel("Tìm mã GD:"));
        add(txtTimKiem);

        txtHienThi = new JTextArea(15, 40);
        add(new JScrollPane(txtHienThi));

        btnThem.addActionListener(e -> themGiaoDich());
        btnXoa.addActionListener(e -> xoaGiaoDich());
        btnHienThi.addActionListener(e -> hienThiDanhSach());

        setVisible(true);
    }

    private void themGiaoDich() {
        try {
            String maGD = txtMaGD.getText();
            String ngayGD = txtNgayGD.getText();
            double donGia = Double.parseDouble(txtDonGia.getText());
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            String loaiGD = (String) cboLoaiGD.getSelectedItem();
            GiaoDich gd = null;

            if ("Vàng".equals(loaiGD)) {
                String loaiVang = txtLoaiVang.getText();
                gd = new GiaoDichVang(maGD, ngayGD, donGia, soLuong, loaiVang);
            } else if ("Tiền tệ".equals(loaiGD)) {
                String loaiTien = txtLoaiTien.getText();
                double tiGia = Double.parseDouble(txtTiGia.getText());
                gd = new GiaoDichTienTe(maGD, ngayGD, donGia, soLuong, loaiTien, tiGia);
            }

            if (gd != null) {
                qlgd.themGiaoDich(gd);
                JOptionPane.showMessageDialog(this, "Thêm giao dịch thành công!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaGiaoDich() {
        String maGD = txtTimKiem.getText();
        qlgd.xoaGiaoDich(maGD);
        JOptionPane.showMessageDialog(this, "Đã xóa giao dịch có mã: " + maGD);
    }

    private void hienThiDanhSach() {
        txtHienThi.setText("");
        for (GiaoDich gd : qlgd.getDanhSach()) {
            txtHienThi.append(gd.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        B8 b8 = new B8();
    }
}
