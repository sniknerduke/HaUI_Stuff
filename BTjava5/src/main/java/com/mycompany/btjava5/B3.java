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

// Lớp cha ThiSinh
class ThiSinh {
    protected String soBaoDanh, hoTen, diaChi, uuTien;

    public ThiSinh(String soBaoDanh, String hoTen, String diaChi, String uuTien) {
        this.soBaoDanh = soBaoDanh;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.uuTien = uuTien;
    }

    public String getSoBaoDanh() { return soBaoDanh; }

    @Override
    public String toString() {
        return soBaoDanh + " - " + hoTen + " - " + diaChi + " - Ưu tiên: " + uuTien;
    }
}

// Lớp thí sinh khối A
class ThiSinhKhoiA extends ThiSinh {
    public ThiSinhKhoiA(String soBaoDanh, String hoTen, String diaChi, String uuTien) {
        super(soBaoDanh, hoTen, diaChi, uuTien);
    }

    @Override
    public String toString() {
        return "Khối A: " + super.toString() + " - Môn: Toán, Lý, Hóa";
    }
}

// Lớp thí sinh khối B
class ThiSinhKhoiB extends ThiSinh {
    public ThiSinhKhoiB(String soBaoDanh, String hoTen, String diaChi, String uuTien) {
        super(soBaoDanh, hoTen, diaChi, uuTien);
    }

    @Override
    public String toString() {
        return "Khối B: " + super.toString() + " - Môn: Toán, Hóa, Sinh";
    }
}

// Lớp thí sinh khối C
class ThiSinhKhoiC extends ThiSinh {
    public ThiSinhKhoiC(String soBaoDanh, String hoTen, String diaChi, String uuTien) {
        super(soBaoDanh, hoTen, diaChi, uuTien);
    }

    @Override
    public String toString() {
        return "Khối C: " + super.toString() + " - Môn: Văn, Sử, Địa";
    }
}

// Lớp quản lý tuyển sinh
class TuyenSinh {
    private List<ThiSinh> danhSach = new ArrayList<>();

    public void themThiSinh(ThiSinh ts) {
        danhSach.add(ts);
    }

    public ThiSinh timTheoSoBaoDanh(String sbd) {
        for (ThiSinh ts : danhSach) {
            if (ts.getSoBaoDanh().equalsIgnoreCase(sbd)) {
                return ts;
            }
        }
        return null;
    }

    public List<ThiSinh> getDanhSach() {
        return danhSach;
    }
}

// Giao diện quản lý tuyển sinh
public class B3 extends JFrame {
    private TuyenSinh qlts = new TuyenSinh();
    private JTextArea txtHienThi;
    private JComboBox<String> cboKhoi;
    private JTextField txtSBD, txtHoTen, txtDiaChi, txtUuTien, txtTimKiem;

    public B3() {
        setTitle("Quản lý Tuyển sinh");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Khối:"));
        cboKhoi = new JComboBox<>(new String[]{"A", "B", "C"});
        add(cboKhoi);

        add(new JLabel("Số báo danh:"));
        txtSBD = new JTextField(10);
        add(txtSBD);

        add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField(15);
        add(txtHoTen);

        add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField(15);
        add(txtDiaChi);

        add(new JLabel("Ưu tiên:"));
        txtUuTien = new JTextField(10);
        add(txtUuTien);

        JButton btnThem = new JButton("Thêm thí sinh");
        JButton btnHienThi = new JButton("Hiển thị tất cả");
        JButton btnTimKiem = new JButton("Tìm theo SBD");

        add(btnThem);
        add(btnHienThi);
        add(btnTimKiem);

        txtTimKiem = new JTextField(10);
        add(txtTimKiem);

        txtHienThi = new JTextArea(15, 40);
        add(new JScrollPane(txtHienThi));

        btnThem.addActionListener(e -> themThiSinh());
        btnHienThi.addActionListener(e -> hienThiTatCa());
        btnTimKiem.addActionListener(e -> timTheoSBD());

        setVisible(true);
    }

    private void themThiSinh() {
        String sbd = txtSBD.getText();
        String hoTen = txtHoTen.getText();
        String diaChi = txtDiaChi.getText();
        String uuTien = txtUuTien.getText();
        String khoi = (String) cboKhoi.getSelectedItem();
        ThiSinh ts = null;

        if ("A".equals(khoi)) {
            ts = new ThiSinhKhoiA(sbd, hoTen, diaChi, uuTien);
        } else if ("B".equals(khoi)) {
            ts = new ThiSinhKhoiB(sbd, hoTen, diaChi, uuTien);
        } else if ("C".equals(khoi)) {
            ts = new ThiSinhKhoiC(sbd, hoTen, diaChi, uuTien);
        }

        if (ts != null) {
            qlts.themThiSinh(ts);
            txtHienThi.append(ts.toString() + "\n");
        }
    }

    private void hienThiTatCa() {
        txtHienThi.setText("");
        for (ThiSinh ts : qlts.getDanhSach()) {
            txtHienThi.append(ts.toString() + "\n");
        }
    }

    private void timTheoSBD() {
        String sbd = txtTimKiem.getText();
        ThiSinh ts = qlts.timTheoSoBaoDanh(sbd);
        txtHienThi.setText(ts != null ? ts.toString() : "Không tìm thấy thí sinh!");
    }

    public static void main(String[] args) {
        new B3();
    }
}

