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

// Lớp Nguoi (Quản lý thông tin cá nhân)
class Nguoi {
    private String hoTen;
    private int tuoi;
    private String ngheNghiep;
    private String namSinh;

    public Nguoi(String hoTen, int tuoi, String namSinh, String ngheNghiep) {
        this.hoTen = hoTen;
        this.tuoi = tuoi;
        this.namSinh = namSinh;
        this.ngheNghiep = ngheNghiep;
    }

    @Override
    public String toString() {
        return hoTen + " - Tuổi: " + tuoi + " - Năm sinh: " + namSinh + " - Nghề: " + ngheNghiep;
    }
}

// Lớp HoDan (Quản lý hộ dân)
class HoDan {
    private int soThanhVien;
    private String soNha;
    private List<Nguoi> danhSachThanhVien;

    public HoDan(int soThanhVien, String soNha) {
        this.soThanhVien = soThanhVien;
        this.soNha = soNha;
        this.danhSachThanhVien = new ArrayList<>();
    }

    public void themThanhVien(Nguoi nguoi) {
        if (danhSachThanhVien.size() < soThanhVien) {
            danhSachThanhVien.add(nguoi);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Hộ dân: Số nhà " + soNha + " - Thành viên: " + soThanhVien + "\n");
        for (Nguoi n : danhSachThanhVien) {
            sb.append("   ").append(n.toString()).append("\n");
        }
        return sb.toString();
    }
}

// Lớp quản lý khu phố
class KhuPho {
    private List<HoDan> danhSachHoDan = new ArrayList<>();

    public void themHoDan(HoDan hoDan) {
        danhSachHoDan.add(hoDan);
    }

    public List<HoDan> getDanhSachHoDan() {
        return danhSachHoDan;
    }
}

// Giao diện quản lý khu phố
public class B4 extends JFrame {
    private KhuPho khuPho = new KhuPho();
    private JTextField txtSoNha, txtSoThanhVien, txtHoTen, txtTuoi, txtNamSinh, txtNghe;
    private JTextArea txtHienThi;
    private HoDan hoDanHienTai;

    public B4() {
        setTitle("Quản lý Dân cư");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Số nhà:"));
        txtSoNha = new JTextField(10);
        add(txtSoNha);

        add(new JLabel("Số thành viên:"));
        txtSoThanhVien = new JTextField(5);
        add(txtSoThanhVien);

        JButton btnThemHoDan = new JButton("Thêm hộ dân");
        add(btnThemHoDan);

        add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField(15);
        add(txtHoTen);

        add(new JLabel("Tuổi:"));
        txtTuoi = new JTextField(5);
        add(txtTuoi);

        add(new JLabel("Năm sinh:"));
        txtNamSinh = new JTextField(10);
        add(txtNamSinh);

        add(new JLabel("Nghề nghiệp:"));
        txtNghe = new JTextField(10);
        add(txtNghe);

        JButton btnThemThanhVien = new JButton("Thêm thành viên");
        add(btnThemThanhVien);

        JButton btnHienThi = new JButton("Hiển thị danh sách");
        add(btnHienThi);

        txtHienThi = new JTextArea(15, 40);
        add(new JScrollPane(txtHienThi));

        btnThemHoDan.addActionListener(e -> themHoDan());
        btnThemThanhVien.addActionListener(e -> themThanhVien());
        btnHienThi.addActionListener(e -> hienThiDanhSach());

        setVisible(true);
    }

    private void themHoDan() {
        String soNha = txtSoNha.getText();
        int soThanhVien = Integer.parseInt(txtSoThanhVien.getText());
        hoDanHienTai = new HoDan(soThanhVien, soNha);
        khuPho.themHoDan(hoDanHienTai);
        JOptionPane.showMessageDialog(this, "Đã thêm hộ dân tại số nhà " + soNha);
    }

    private void themThanhVien() {
        if (hoDanHienTai == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm hộ dân trước!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String hoTen = txtHoTen.getText();
        int tuoi = Integer.parseInt(txtTuoi.getText());
        String namSinh = txtNamSinh.getText();
        String nghe = txtNghe.getText();
        hoDanHienTai.themThanhVien(new Nguoi(hoTen, tuoi, namSinh, nghe));
        JOptionPane.showMessageDialog(this, "Thêm thành viên thành công!");
    }

    private void hienThiDanhSach() {
        txtHienThi.setText("");
        for (HoDan hoDan : khuPho.getDanhSachHoDan()) {
            txtHienThi.append(hoDan.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new B4();
    }
}

