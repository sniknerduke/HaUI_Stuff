package com.example.ui;

import com.example.dao.SinhVienDao;
import com.example.model.SinhVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MainForm extends JFrame {

    private static final String[] NGANH_LIST = {
            "Công nghệ thông tin",
            "Kinh tế",
            "Kế toán",
            "Điện tử viễn thông",
            "Toán học",
    };

    private final SinhVienDao dao = new SinhVienDao();
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ── Form fields ──
    private JTextField txtHoTen, txtMaSinhVien, txtNgaySinh;
    private JRadioButton rdoNam, rdoNu;
    private ButtonGroup bgGioiTinh;
    private JCheckBox[] chkNganh;

    private JTable tblSinhVien;
    private DefaultTableModel tableModel;

    // Trạng thái: đang "sửa" hay "nhập mới"
    private boolean dangSua = false;

    // ════════════════════════════════════════
    public MainForm() {
        setTitle("Quản Lý Sinh Viên");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(700, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        buildUI();

        // Override close button → hỏi xác nhận
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                thoatChuongTrinh();
            }
        });
    }

    // ════════════════════════════════════════
    private void buildUI() {
        JPanel pnlInfo = new JPanel(new GridBagLayout());
        pnlInfo.setBorder(BorderFactory.createTitledBorder("Thông tin sinh viên"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        addLabel(pnlInfo, "Họ tên:", gbc, 0, 0);
        txtHoTen = new JTextField(30);
        addComp(pnlInfo, txtHoTen, gbc, 1, 0);

        addLabel(pnlInfo, "Mã SV:", gbc, 0, 1);
        txtMaSinhVien = new JTextField(30);
        addComp(pnlInfo, txtMaSinhVien, gbc, 1, 1);

        addLabel(pnlInfo, "Ngày sinh (dd/MM/yyyy):", gbc, 0, 2);
        txtNgaySinh = new JTextField(30);
        addComp(pnlInfo, txtNgaySinh, gbc, 1, 2);

        addLabel(pnlInfo, "Giới tính:", gbc, 0, 3);
        rdoNam = new JRadioButton("Nam", true);
        rdoNu = new JRadioButton("Nữ");
        bgGioiTinh = new ButtonGroup();
        bgGioiTinh.add(rdoNam);
        bgGioiTinh.add(rdoNu);
        JPanel pnlGT = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        pnlGT.add(rdoNam);
        pnlGT.add(rdoNu);
        addComp(pnlInfo, pnlGT, gbc, 1, 3);

        addLabel(pnlInfo, "Ngành học (tối đa 2):", gbc, 0, 4);
        JPanel pnlNganh = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
        chkNganh = new JCheckBox[NGANH_LIST.length];
        for (int i = 0; i < NGANH_LIST.length; i++) {
            chkNganh[i] = new JCheckBox(NGANH_LIST[i]);
            final int idx = i;
            chkNganh[i].addActionListener(e -> giamSatChonNganh(idx));
            pnlNganh.add(chkNganh[i]);
        }
        addComp(pnlInfo, pnlNganh, gbc, 1, 4);

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnNhap = new JButton("Nhập");
        btnNhap.setPreferredSize(new Dimension(80, 30));
        btnNhap.addActionListener(e -> nhanNhap());

        JButton btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(80, 30));
        btnThem.addActionListener(e -> nhanThem());

        JButton btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(80, 30));
        btnSua.addActionListener(e -> nhanSua());

        JButton btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(80, 30));
        btnXoa.addActionListener(e -> nhanXoa());

        JButton btnThoat = new JButton("Kết thúc chương trình");
        btnThoat.setPreferredSize(new Dimension(180, 30));
        btnThoat.addActionListener(e -> thoatChuongTrinh());

        pnlBtn.add(btnNhap);
        pnlBtn.add(btnThem);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnThoat);

        // ── Table ──
        tableModel = new DefaultTableModel(new Object[]{"Mã SV", "Họ Tên", "Ngày Sinh", "Giới Tính", "Ngành Học"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblSinhVien = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblSinhVien);
        scrollPane.setPreferredSize(new Dimension(650, 200));
        JPanel pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBorder(BorderFactory.createTitledBorder("Danh sách sinh viên"));
        pnlTable.add(scrollPane, BorderLayout.CENTER);

        loadTableData();

        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.add(pnlInfo, BorderLayout.NORTH);
        pnlCenter.add(pnlTable, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);
        add(pnlBtn, BorderLayout.SOUTH);
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<SinhVien> list = dao.getAll();
        for (SinhVien sv : list) {
            tableModel.addRow(new Object[]{
                    sv.getMaSinhVien(),
                    sv.getHoTen(),
                    sv.getNgaySinh().format(fmt),
                    sv.getGioiTinh(),
                    sv.getNganhHoc()
            });
        }
    }

    private void nhanNhap() {
        SinhVien sv = layDuLieuForm();
        if (sv == null) return;

        if (dangSua) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Có cập nhật thông tin mới hay không?",
                    "Xác nhận cập nhật",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.update(sv)) {
                    JOptionPane.showMessageDialog(this,
                            "Cập nhật thông tin thành công!",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadTableData();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Cập nhật thất bại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
            dangSua = false;
        } else {
            if (dao.insert(sv)) {
                JOptionPane.showMessageDialog(this,
                        "Nhập bản ghi thành công!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Nhập thất bại! Mã sinh viên có thể đã tồn tại.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void nhanThem() {
        dangSua = false;
        xoaForm();
        txtHoTen.requestFocus();
    }

    private void nhanSua() {
        String ma = JOptionPane.showInputDialog(this,
                "Nhập mã sinh viên cần sửa:",
                "Sửa sinh viên",
                JOptionPane.QUESTION_MESSAGE);
        if (ma == null || ma.trim().isEmpty()) return;

        SinhVien sv = dao.get(ma.trim());
        if (sv == null) {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy sinh viên với mã: " + ma.trim(),
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        hienThiLenForm(sv);
        dangSua = true;
        txtHoTen.requestFocus();
    }

    private void nhanXoa() {
        String ma = JOptionPane.showInputDialog(this,
                "Nhập mã sinh viên cần xóa:",
                "Xóa sinh viên",
                JOptionPane.QUESTION_MESSAGE);
        if (ma == null || ma.trim().isEmpty()) return;

        SinhVien sv = dao.get(ma.trim());
        if (sv == null) {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy sinh viên với mã: " + ma.trim(),
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa sinh viên:\n"
                        + "Họ tên : " + sv.getHoTen() + "\n"
                        + "Mã SV  : " + sv.getMaSinhVien(),
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(ma.trim())) {
                JOptionPane.showMessageDialog(this,
                        "Sinh viên có mã sinh viên " + ma.trim() + " đã bị xóa.",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                xoaForm();
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Xóa thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /** NÚT KẾT THÚC CHƯƠNG TRÌNH */
    private void thoatChuongTrinh() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn kết thúc chương trình?",
                "Kết thúc chương trình",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }

    // ════════ Helpers ════════

    /** Đọc dữ liệu từ form, trả null nếu không hợp lệ */
    private SinhVien layDuLieuForm() {
        String hoTen = txtHoTen.getText().trim();
        String ma = txtMaSinhVien.getText().trim();
        String ngay = txtNgaySinh.getText().trim();

        if (hoTen.isEmpty() || ma.isEmpty() || ngay.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng điền đầy đủ họ tên, mã sinh viên và ngày sinh.",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        LocalDate ngaySinh;
        try {
            ngaySinh = LocalDate.parse(ngay, fmt);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ngày sinh không hợp lệ. Định dạng đúng: dd/MM/yyyy",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        String gioiTinh = rdoNam.isSelected() ? "Nam" : "Nữ";

        StringBuilder sb = new StringBuilder();
        for (JCheckBox chk : chkNganh) {
            if (chk.isSelected()) {
                if (!sb.isEmpty()) sb.append(",");
                sb.append(chk.getText());
            }
        }
        if (sb.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn ít nhất 1 ngành học.",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        return new SinhVien(ma, hoTen, ngaySinh, gioiTinh, sb.toString());
    }

    /** Xóa trắng toàn bộ form */
    private void xoaForm() {
        txtHoTen.setText("");
        txtMaSinhVien.setText("");
        txtNgaySinh.setText("");
        rdoNam.setSelected(true);
        for (JCheckBox chk : chkNganh) chk.setSelected(false);
    }

    /** Hiển thị thông tin SinhVien lên form */
    private void hienThiLenForm(SinhVien sv) {
        txtHoTen.setText(sv.getHoTen());
        txtMaSinhVien.setText(sv.getMaSinhVien());
        txtNgaySinh.setText(sv.getNgaySinh().format(fmt));
        if ("Nữ".equals(sv.getGioiTinh())) rdoNu.setSelected(true);
        else rdoNam.setSelected(true);

        String[] nganhs = sv.getNganhHoc().split(",");
        for (JCheckBox chk : chkNganh) {
            chk.setSelected(false);
            for (String n : nganhs) {
                if (chk.getText().equalsIgnoreCase(n.trim())) {
                    chk.setSelected(true);
                }
            }
        }
    }

    /** Giới hạn chỉ chọn tối đa 2 ngành */
    private void giamSatChonNganh(int vừaChọn) {
        int soChon = 0;
        for (JCheckBox chk : chkNganh) if (chk.isSelected()) soChon++;
        if (soChon > 2) {
            chkNganh[vừaChọn].setSelected(false);
            JOptionPane.showMessageDialog(this,
                    "Mỗi sinh viên chỉ được chọn tối đa 2 ngành học.",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    // ════════ GridBagLayout helpers ════════
    private void addLabel(JPanel p, String text, GridBagConstraints g, int x, int y) {
        g.gridx = x;
        g.gridy = y;
        g.weightx = 0;
        p.add(new JLabel(text), g);
    }

    private void addComp(JPanel p, JComponent c, GridBagConstraints g, int x, int y) {
        g.gridx = x;
        g.gridy = y;
        g.weightx = 1;
        p.add(c, g);
    }

    // ════════ main ════════
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new MainForm().setVisible(true);
        });
    }
}

