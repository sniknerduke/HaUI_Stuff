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

// Lớp cha TaiLieu
class TaiLieu {
    protected String maTaiLieu, nhaXuatBan;
    protected int soBanPhatHanh;

    public TaiLieu(String maTaiLieu, String nhaXuatBan, int soBanPhatHanh) {
        this.maTaiLieu = maTaiLieu;
        this.nhaXuatBan = nhaXuatBan;
        this.soBanPhatHanh = soBanPhatHanh;
    }

    @Override
    public String toString() {
        return maTaiLieu + " - " + nhaXuatBan + " - Số bản: " + soBanPhatHanh;
    }
}

// Lớp Sách kế thừa từ TaiLieu
class Sach extends TaiLieu {
    private String tenTacGia;
    private int soTrang;

    public Sach(String maTaiLieu, String nhaXuatBan, int soBanPhatHanh, String tenTacGia, int soTrang) {
        super(maTaiLieu, nhaXuatBan, soBanPhatHanh);
        this.tenTacGia = tenTacGia;
        this.soTrang = soTrang;
    }

    @Override
    public String toString() {
        return "Sách: " + super.toString() + " - Tác giả: " + tenTacGia + " - Số trang: " + soTrang;
    }
}

// Lớp Tạp chí kế thừa từ TaiLieu
class TapChi extends TaiLieu {
    private int soPhatHanh, thangPhatHanh;

    public TapChi(String maTaiLieu, String nhaXuatBan, int soBanPhatHanh, int soPhatHanh, int thangPhatHanh) {
        super(maTaiLieu, nhaXuatBan, soBanPhatHanh);
        this.soPhatHanh = soPhatHanh;
        this.thangPhatHanh = thangPhatHanh;
    }

    @Override
    public String toString() {
        return "Tạp chí: " + super.toString() + " - Số phát hành: " + soPhatHanh + " - Tháng: " + thangPhatHanh;
    }
}

// Lớp Báo kế thừa từ TaiLieu
class Bao extends TaiLieu {
    private String ngayPhatHanh;

    public Bao(String maTaiLieu, String nhaXuatBan, int soBanPhatHanh, String ngayPhatHanh) {
        super(maTaiLieu, nhaXuatBan, soBanPhatHanh);
        this.ngayPhatHanh = ngayPhatHanh;
    }

    @Override
    public String toString() {
        return "Báo: " + super.toString() + " - Ngày phát hành: " + ngayPhatHanh;
    }
}

// Lớp quản lý thư viện
class QuanLySach {
    private List<TaiLieu> danhSach = new ArrayList<>();

    public void themTaiLieu(TaiLieu tl) {
        danhSach.add(tl);
    }

    public List<TaiLieu> timKiemTheoLoai(String loai) {
        List<TaiLieu> ketQua = new ArrayList<>();
        for (TaiLieu tl : danhSach) {
            if (tl.getClass().getSimpleName().equalsIgnoreCase(loai)) {
                ketQua.add(tl);
            }
        }
        return ketQua;
    }

    public List<TaiLieu> getDanhSach() {
        return danhSach;
    }
}

// Giao diện quản lý thư viện
public class B2 extends JFrame {
    private QuanLySach qls = new QuanLySach();
    private JTextArea txtHienThi;
    private JComboBox<String> cboLoaiTaiLieu;
    private JTextField txtMa, txtNXB, txtSoBan, txtTacGia, txtSoTrang, txtSoPhatHanh, txtThang, txtNgay;

    public B2() {
        setTitle("Quản lý Thư viện");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Loại tài liệu:"));
        cboLoaiTaiLieu = new JComboBox<>(new String[]{"Sách", "Tạp chí", "Báo"});
        add(cboLoaiTaiLieu);

        add(new JLabel("Mã tài liệu:"));
        txtMa = new JTextField(10);
        add(txtMa);

        add(new JLabel("Nhà xuất bản:"));
        txtNXB = new JTextField(10);
        add(txtNXB);

        add(new JLabel("Số bản phát hành:"));
        txtSoBan = new JTextField(5);
        add(txtSoBan);

        add(new JLabel("Tác giả (Sách):"));
        txtTacGia = new JTextField(10);
        add(txtTacGia);

        add(new JLabel("Số trang (Sách):"));
        txtSoTrang = new JTextField(5);
        add(txtSoTrang);

        add(new JLabel("Số phát hành (Tạp chí):"));
        txtSoPhatHanh = new JTextField(5);
        add(txtSoPhatHanh);

        add(new JLabel("Tháng phát hành (Tạp chí):"));
        txtThang = new JTextField(5);
        add(txtThang);

        add(new JLabel("Ngày phát hành (Báo):"));
        txtNgay = new JTextField(10);
        add(txtNgay);

        JButton btnThem = new JButton("Thêm tài liệu");
        JButton btnHienThi = new JButton("Hiển thị tất cả");
        JButton btnTimKiem = new JButton("Tìm theo loại");

        add(btnThem);
        add(btnHienThi);
        add(btnTimKiem);

        txtHienThi = new JTextArea(15, 40);
        add(new JScrollPane(txtHienThi));

        btnThem.addActionListener(e -> themTaiLieu());
        btnHienThi.addActionListener(e -> hienThiTatCa());
        btnTimKiem.addActionListener(e -> timTheoLoai());

        setVisible(true);
    }

    private void themTaiLieu() {
        String ma = txtMa.getText();
        String nxb = txtNXB.getText();
        int soBan = Integer.parseInt(txtSoBan.getText());
        String loai = (String) cboLoaiTaiLieu.getSelectedItem();
        TaiLieu tl = null;

        if ("Sách".equals(loai)) {
            tl = new Sach(ma, nxb, soBan, txtTacGia.getText(), Integer.parseInt(txtSoTrang.getText()));
        } else if ("Tạp chí".equals(loai)) {
            tl = new TapChi(ma, nxb, soBan, Integer.parseInt(txtSoPhatHanh.getText()), Integer.parseInt(txtThang.getText()));
        } else if ("Báo".equals(loai)) {
            tl = new Bao(ma, nxb, soBan, txtNgay.getText());
        }

        if (tl != null) {
            qls.themTaiLieu(tl);
            txtHienThi.append(tl.toString() + "\n");
        }
    }

    private void hienThiTatCa() {
        txtHienThi.setText("");
        for (TaiLieu tl : qls.getDanhSach()) {
            txtHienThi.append(tl.toString() + "\n");
        }
    }

    private void timTheoLoai() {
        String loai = (String) cboLoaiTaiLieu.getSelectedItem();
        txtHienThi.setText("");
        for (TaiLieu tl : qls.timKiemTheoLoai(loai)) {
            txtHienThi.append(tl.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new B2();
    }
}
