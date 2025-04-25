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

// Lớp cha CanBo
class CanBo {
    protected String hoTen, namSinh, gioiTinh, diaChi;

    public CanBo(String hoTen, String namSinh, String gioiTinh, String diaChi) {
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
    }

    public String getHoTen() { return hoTen; }
    public String getNamSinh() { return namSinh; }
    public String getGioiTinh() { return gioiTinh; }
    public String getDiaChi() { return diaChi; }

    @Override
    public String toString() {
        return hoTen + " - " + namSinh + " - " + gioiTinh + " - " + diaChi;
    }
}

// Lớp Công nhân
class CongNhan extends CanBo {
    private int bac;

    public CongNhan(String hoTen, String namSinh, String gioiTinh, String diaChi, int bac) {
        super(hoTen, namSinh, gioiTinh, diaChi);
        this.bac = bac;
    }

    public int getBac() { return bac; }

    @Override
    public String toString() {
        return "Công nhân: " + super.toString() + " - Bậc: " + bac;
    }
}

// Lớp Kỹ sư
class KySu extends CanBo {
    private String nganhDaoTao;

    public KySu(String hoTen, String namSinh, String gioiTinh, String diaChi, String nganhDaoTao) {
        super(hoTen, namSinh, gioiTinh, diaChi);
        this.nganhDaoTao = nganhDaoTao;
    }

    public String getNganhDaoTao() { return nganhDaoTao; }

    @Override
    public String toString() {
        return "Kỹ sư: " + super.toString() + " - Ngành: " + nganhDaoTao;
    }
}

// Lớp Nhân viên phục vụ
class NhanVien extends CanBo {
    private String congViec;

    public NhanVien(String hoTen, String namSinh, String gioiTinh, String diaChi, String congViec) {
        super(hoTen, namSinh, gioiTinh, diaChi);
        this.congViec = congViec;
    }

    public String getCongViec() { return congViec; }

    @Override
    public String toString() {
        return "Nhân viên: " + super.toString() + " - Công việc: " + congViec;
    }
}

// Lớp quản lý nhân viên
class QLCB {
    private List<CanBo> danhSach = new ArrayList<>();

    public void themCanBo(CanBo cb) {
        danhSach.add(cb);
    }

    public List<CanBo> timKiemTheoTen(String ten) {
        List<CanBo> ketQua = new ArrayList<>();
        for (CanBo cb : danhSach) {
            if (cb.getHoTen().toLowerCase().contains(ten.toLowerCase())) {
                ketQua.add(cb);
            }
        }
        return ketQua;
    }

    public List<CanBo> getDanhSach() {
        return danhSach;
    }
}

// Lớp GUI chính
public class B1 extends JFrame {
    private QLCB qlcb = new QLCB();
    private JTextField txtHoTen, txtNamSinh, txtGioiTinh, txtDiaChi, txtBac, txtNganhDaoTao, txtCongViec, txtTimKiem;
    private JTextArea txtHienThi;
    private JComboBox<String> cboLoaiCanBo;

    public B1() {
        setTitle("Quản lý nhân viên");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Loại cán bộ:"));
        cboLoaiCanBo = new JComboBox<>(new String[]{"Công nhân", "Kỹ sư", "Nhân viên"});
        add(cboLoaiCanBo);

        add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField(15);
        add(txtHoTen);

        add(new JLabel("Năm sinh:"));
        txtNamSinh = new JTextField(10);
        add(txtNamSinh);

        add(new JLabel("Giới tính:"));
        txtGioiTinh = new JTextField(10);
        add(txtGioiTinh);

        add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField(15);
        add(txtDiaChi);

        add(new JLabel("Bậc (Công nhân):"));
        txtBac = new JTextField(5);
        add(txtBac);

        add(new JLabel("Ngành đào tạo (Kỹ sư):"));
        txtNganhDaoTao = new JTextField(10);
        add(txtNganhDaoTao);

        add(new JLabel("Công việc (Nhân viên):"));
        txtCongViec = new JTextField(10);
        add(txtCongViec);

        JButton btnThem = new JButton("Thêm cán bộ");
        add(btnThem);

        JButton btnTimKiem = new JButton("Tìm kiếm");
        add(btnTimKiem);

        txtTimKiem = new JTextField(10);
        add(txtTimKiem);

        txtHienThi = new JTextArea(15, 40);
        add(new JScrollPane(txtHienThi));

        btnThem.addActionListener(e -> themCanBo());
        btnTimKiem.addActionListener(e -> timKiem());

        setVisible(true);
    }

    private void themCanBo() {
        String hoTen = txtHoTen.getText();
        String namSinh = txtNamSinh.getText();
        String gioiTinh = txtGioiTinh.getText();
        String diaChi = txtDiaChi.getText();
        String loai = (String) cboLoaiCanBo.getSelectedItem();

        CanBo cb = null;
        if ("Công nhân".equals(loai)) {
            int bac = Integer.parseInt(txtBac.getText());
            cb = new CongNhan(hoTen, namSinh, gioiTinh, diaChi, bac);
        } else if ("Kỹ sư".equals(loai)) {
            cb = new KySu(hoTen, namSinh, gioiTinh, diaChi, txtNganhDaoTao.getText());
        } else if ("Nhân viên".equals(loai)) {
            cb = new NhanVien(hoTen, namSinh, gioiTinh, diaChi, txtCongViec.getText());
        }

        if (cb != null) {
            qlcb.themCanBo(cb);
            txtHienThi.append(cb.toString() + "\n");
        }
    }

    private void timKiem() {
        String ten = txtTimKiem.getText();
        txtHienThi.setText("");
        for (CanBo cb : qlcb.timKiemTheoTen(ten)) {
            txtHienThi.append(cb.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new B1();
    }
}

