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

// Lớp SoTietKiem (Lưu thông tin sổ tiết kiệm)
class SoTietKiem {
    private String maSo, hoTen, ngayGui;
    private double soTienGui, laiSuat;
    private int kyHan; // Kỳ hạn tính theo tháng

    public SoTietKiem(String maSo, String hoTen, String ngayGui, double soTienGui, double laiSuat, int kyHan) {
        this.maSo = maSo;
        this.hoTen = hoTen;
        this.ngayGui = ngayGui;
        this.soTienGui = soTienGui;
        this.laiSuat = laiSuat;
        this.kyHan = kyHan;
    }

    public String getMaSo() { return maSo; }

    public double tinhLai() {
        return soTienGui * (laiSuat / 100) * kyHan / 12; // Lãi suất theo năm
    }

    @Override
    public String toString() {
        return "Sổ TK: " + maSo + " - " + hoTen + " - Ngày gửi: " + ngayGui +
               " - Số tiền: " + soTienGui + " VNĐ - Kỳ hạn: " + kyHan + " tháng - Tiền lãi: " + tinhLai() + " VNĐ";
    }
}

// Lớp quản lý sổ tiết kiệm
class QuanLyTietKiem {
    private List<SoTietKiem> danhSach = new ArrayList<>();

    public void themSoTietKiem(SoTietKiem stk) {
        danhSach.add(stk);
    }

    public void xoaSoTietKiem(String maSo) {
        danhSach.removeIf(stk -> stk.getMaSo().equals(maSo));
    }

    public List<SoTietKiem> getDanhSach() {
        return danhSach;
    }
}

// Giao diện quản lý sổ tiết kiệm
public class B9 extends JFrame {
    private QuanLyTietKiem qltk = new QuanLyTietKiem();
    private JTextField txtMaSo, txtHoTen, txtNgayGui, txtSoTienGui, txtLaiSuat, txtKyHan, txtTimKiem;
    private JTextArea txtHienThi;

    public B9() {
        setTitle("Quản lý Sổ Tiết kiệm");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Mã sổ:"));
        txtMaSo = new JTextField(10);
        add(txtMaSo);

        add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField(15);
        add(txtHoTen);

        add(new JLabel("Ngày gửi:"));
        txtNgayGui = new JTextField(10);
        add(txtNgayGui);

        add(new JLabel("Số tiền gửi:"));
        txtSoTienGui = new JTextField(10);
        add(txtSoTienGui);

        add(new JLabel("Lãi suất (%/năm):"));
        txtLaiSuat = new JTextField(5);
        add(txtLaiSuat);

        add(new JLabel("Kỳ hạn (tháng):"));
        txtKyHan = new JTextField(5);
        add(txtKyHan);

        JButton btnThem = new JButton("Thêm sổ");
        JButton btnXoa = new JButton("Xóa sổ");
        JButton btnHienThi = new JButton("Hiển thị danh sách");

        add(btnThem);
        add(btnXoa);
        add(btnHienThi);

        txtTimKiem = new JTextField(10);
        add(new JLabel("Tìm mã sổ:"));
        add(txtTimKiem);

        txtHienThi = new JTextArea(15, 40);
        add(new JScrollPane(txtHienThi));

        btnThem.addActionListener(e -> themSoTietKiem());
        btnXoa.addActionListener(e -> xoaSoTietKiem());
        btnHienThi.addActionListener(e -> hienThiDanhSach());

        setVisible(true);
    }

    private void themSoTietKiem() {
        try {
            String maSo = txtMaSo.getText();
            String hoTen = txtHoTen.getText();
            String ngayGui = txtNgayGui.getText();
            double soTienGui = Double.parseDouble(txtSoTienGui.getText());
            double laiSuat = Double.parseDouble(txtLaiSuat.getText());
            int kyHan = Integer.parseInt(txtKyHan.getText());

            SoTietKiem stk = new SoTietKiem(maSo, hoTen, ngayGui, soTienGui, laiSuat, kyHan);
            qltk.themSoTietKiem(stk);
            JOptionPane.showMessageDialog(this, "Thêm sổ tiết kiệm thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaSoTietKiem() {
        String maSo = txtTimKiem.getText();
        qltk.xoaSoTietKiem(maSo);
        JOptionPane.showMessageDialog(this, "Đã xóa sổ tiết kiệm có mã: " + maSo);
    }

    private void hienThiDanhSach() {
        txtHienThi.setText("");
        for (SoTietKiem stk : qltk.getDanhSach()) {
            txtHienThi.append(stk.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new B9();
    }
}
