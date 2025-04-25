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

// Lớp KhachHang (Lưu thông tin khách hàng)
class KhachHang {
    private String hoTen, soNha, maCongTo;
    private int chiSoCu, chiSoMoi;

    public KhachHang(String hoTen, String soNha, String maCongTo, int chiSoCu, int chiSoMoi) {
        this.hoTen = hoTen;
        this.soNha = soNha;
        this.maCongTo = maCongTo;
        this.chiSoCu = chiSoCu;
        this.chiSoMoi = chiSoMoi;
    }

    public String getMaCongTo() { return maCongTo; }

    public int tinhTienDien() {
        return (chiSoMoi - chiSoCu) * 3000;  // Giá điện là 3000 VNĐ/kWh
    }

    @Override
    public String toString() {
        return "Khách: " + hoTen + " - Số nhà: " + soNha + " - Công tơ: " + maCongTo +
               " - Tiêu thụ: " + (chiSoMoi - chiSoCu) + " kWh - Tiền điện: " + tinhTienDien() + " VNĐ";
    }
}

// Lớp Quản lý hóa đơn tiền điện
class QuanLyHoaDon {
    private List<KhachHang> danhSach = new ArrayList<>();

    public void themKhachHang(KhachHang kh) {
        danhSach.add(kh);
    }

    public void xoaKhachHang(String maCongTo) {
        danhSach.removeIf(kh -> kh.getMaCongTo().equals(maCongTo));
    }

    public List<KhachHang> getDanhSach() {
        return danhSach;
    }
}

// Giao diện quản lý hóa đơn tiền điện
public class B6 extends JFrame {
    private QuanLyHoaDon qlhd = new QuanLyHoaDon();
    private JTextField txtHoTen, txtSoNha, txtMaCongTo, txtChiSoCu, txtChiSoMoi, txtTimKiem;
    private JTextArea txtHienThi;

    public B6() {
        setTitle("Quản lý Hóa đơn Tiền điện");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField(15);
        add(txtHoTen);

        add(new JLabel("Số nhà:"));
        txtSoNha = new JTextField(10);
        add(txtSoNha);

        add(new JLabel("Mã công tơ:"));
        txtMaCongTo = new JTextField(10);
        add(txtMaCongTo);

        add(new JLabel("Chỉ số cũ:"));
        txtChiSoCu = new JTextField(5);
        add(txtChiSoCu);

        add(new JLabel("Chỉ số mới:"));
        txtChiSoMoi = new JTextField(5);
        add(txtChiSoMoi);

        JButton btnThem = new JButton("Thêm hóa đơn");
        JButton btnXoa = new JButton("Xóa hóa đơn");
        JButton btnHienThi = new JButton("Hiển thị danh sách");

        add(btnThem);
        add(btnXoa);
        add(btnHienThi);

        txtTimKiem = new JTextField(10);
        add(new JLabel("Tìm mã công tơ:"));
        add(txtTimKiem);

        txtHienThi = new JTextArea(15, 40);
        add(new JScrollPane(txtHienThi));

        btnThem.addActionListener(e -> themHoaDon());
        btnXoa.addActionListener(e -> xoaHoaDon());
        btnHienThi.addActionListener(e -> hienThiDanhSach());

        setVisible(true);
    }

    private void themHoaDon() {
        try {
            String hoTen = txtHoTen.getText();
            String soNha = txtSoNha.getText();
            String maCongTo = txtMaCongTo.getText();
            int chiSoCu = Integer.parseInt(txtChiSoCu.getText());
            int chiSoMoi = Integer.parseInt(txtChiSoMoi.getText());

            if (chiSoMoi < chiSoCu) {
                JOptionPane.showMessageDialog(this, "Chỉ số mới phải lớn hơn chỉ số cũ!", "Lỗi nhập", JOptionPane.ERROR_MESSAGE);
                return;
            }

            KhachHang kh = new KhachHang(hoTen, soNha, maCongTo, chiSoCu, chiSoMoi);
            qlhd.themKhachHang(kh);
            JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaHoaDon() {
        String maCongTo = txtTimKiem.getText();
        qlhd.xoaKhachHang(maCongTo);
        JOptionPane.showMessageDialog(this, "Đã xóa hóa đơn của công tơ: " + maCongTo);
    }

    private void hienThiDanhSach() {
        txtHienThi.setText("");
        for (KhachHang kh : qlhd.getDanhSach()) {
            txtHienThi.append(kh.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new B6();
    }
}

