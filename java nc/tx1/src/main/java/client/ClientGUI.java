package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientGUI extends JFrame {

    // Các thành phần giao diện
    private JLabel lblStatus;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chkManageMode;

    private JButton btnConnect;
    private JButton btnLogin;
    private JButton btnCreate;
    private JButton btnAddNew;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnExit;

    // Các thành phần mạng TCP
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9999;

    public ClientGUI() {
        setTitle("Client - Quản lý tài khoản");
        setSize(480, 420); // Tăng size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        // Nền trắng và padding
        getContentPane().setBackground(Color.WHITE);
        ((JComponent)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initComponents();
        layoutComponents();
        addEventHandlers();

        // Đặt trạng thái mặc định ban đầu ban đầu (chỉ đăng nhập)
        updateUIMode(false);
    }

    private void styleButton(JButton btn) {
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.BLACK);
                btn.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(Color.BLACK);
            }
        });
    }

    private void styleTextField(JTextField tf) {
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setForeground(Color.BLACK);
        tf.setBackground(Color.WHITE);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }

    private void initComponents() {
        lblStatus = new JLabel("Status: Chưa kết nối", SwingConstants.CENTER);
        lblStatus.setForeground(Color.BLACK); // Đổi thành màu Đen theo phong cách monochrome
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));

        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        styleTextField(txtUsername);
        styleTextField(txtPassword);
        
        chkManageMode = new JCheckBox("Chế độ quản lý server");
        chkManageMode.setFont(new Font("Segoe UI", Font.BOLD, 13));
        chkManageMode.setBackground(Color.WHITE);
        chkManageMode.setForeground(Color.BLACK);
        chkManageMode.setFocusPainted(false);

        btnConnect = new JButton("Connect");
        styleButton(btnConnect);
        
        btnExit = new JButton("Thoát");
        styleButton(btnExit);

        btnLogin = new JButton("Đăng nhập");
        styleButton(btnLogin);

        btnCreate = new JButton("Tạo");
        styleButton(btnCreate);

        btnAddNew = new JButton("Thêm mới");
        styleButton(btnAddNew);

        btnEdit = new JButton("Sửa");
        styleButton(btnEdit);

        btnDelete = new JButton("Xóa");
        styleButton(btnDelete);
    }

    private void layoutComponents() {
        JPanel panelTop = new JPanel(new BorderLayout(15, 0));
        panelTop.setBackground(Color.WHITE);
        panelTop.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK),
            BorderFactory.createEmptyBorder(0, 0, 15, 0)
        ));
        panelTop.add(btnConnect, BorderLayout.WEST);
        panelTop.add(lblStatus, BorderLayout.CENTER);
        panelTop.add(btnExit, BorderLayout.EAST);

        JPanel panelCenter = new JPanel(new GridBagLayout());
        panelCenter.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUser = new JLabel("Username:");
        lblUser.setForeground(Color.BLACK);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 0; gbc.gridy = 0;
        panelCenter.add(lblUser, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 1.0;
        panelCenter.add(txtUsername, gbc);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setForeground(Color.BLACK);
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.0;
        panelCenter.add(lblPass, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 1.0;
        panelCenter.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        panelCenter.add(chkManageMode, gbc);

        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBottom.setBackground(Color.WHITE);
        panelBottom.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        panelBottom.add(btnLogin);
        panelBottom.add(btnCreate);
        panelBottom.add(btnAddNew);
        panelBottom.add(btnEdit);
        panelBottom.add(btnDelete);

        add(panelTop, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }

    private void addEventHandlers() {
        // Sự kiện CheckBox
        chkManageMode.addItemListener(e -> {
            boolean isChecked = (e.getStateChange() == ItemEvent.SELECTED);
            updateUIMode(isChecked);
        });

        // 1. Nút Connect
        btnConnect.addActionListener(e -> connectToServer());

        // 2. Nút Login
        btnLogin.addActionListener(e -> {
            String u = txtUsername.getText();
            String p = new String(txtPassword.getPassword());
            if(u.isEmpty() || p.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Username và Password!");
                 return;
            }
            String res = sendCommand("LOGIN|" + u + "|" + p);
            if ("SUCCESS".equals(res)) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Đăng nhập thất bại");
            }
        });

        // 3. Nút Create
        btnCreate.addActionListener(e -> {
            String u = txtUsername.getText();
            String p = new String(txtPassword.getPassword());
            if(u.isEmpty() || p.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Username và Password!");
                 return;
            }
            String res = sendCommand("CREATE|" + u + "|" + p);
            if ("SUCCESS".equals(res)) {
                JOptionPane.showMessageDialog(this, "Tạo tài khoản thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Tạo tài khoản thất bại (Tài khoản đã tồn tại)");
            }
        });

        // 4. Nút Add new
        btnAddNew.addActionListener(e -> {
            txtUsername.setText("");
            txtPassword.setText("");
            txtUsername.requestFocus();
            JOptionPane.showMessageDialog(this, "Đã làm sạch các trường nhập liệu. Vui lòng nhập thông tin tài khoản mới!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        // 5. Nút Edit
        btnEdit.addActionListener(e -> {
            String u = JOptionPane.showInputDialog(this, "Nhập tên tài khoản cần sửa:");
            if (u != null && !u.trim().isEmpty()) {
                String res = sendCommand("READ|" + u);
                if (res != null && res.startsWith("EXISTS|")) {
                    String oldPass = res.split("\\|")[1];
                    // Hiển thị lên textbox
                    txtUsername.setText(u);
                    txtPassword.setText(oldPass);
                    
                    // Cho phép sửa mật khẩu trên hộp thoại custom
                    JPasswordField pf = new JPasswordField(oldPass);
                    Object[] message = { "Username: " + u, "Mật khẩu mới:", pf };
                    int option = JOptionPane.showConfirmDialog(this, message, "Sửa mật khẩu", JOptionPane.OK_CANCEL_OPTION);
                    
                    if (option == JOptionPane.OK_OPTION) {
                        String newPass = new String(pf.getPassword());
                        
                        // Yêu cầu xác nhận yes/no
                        Object[] options = {"yes", "no"};
                        int confirm = JOptionPane.showOptionDialog(this, "Có cập nhật thông tin sửa đổi hay không?", "Xác nhận cập nhật", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (confirm == 0) {
                            String updateRes = sendCommand("UPDATE|" + u + "|" + newPass);
                            if ("SUCCESS".equals(updateRes)) {
                                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                                txtPassword.setText(newPass);
                            } else {
                                JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Đã bỏ qua");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Tài khoản không tồn tại");
                }
            }
        });

        // 6. Nút Delete
        btnDelete.addActionListener(e -> {
            String u = JOptionPane.showInputDialog(this, "Nhập tên tài khoản cần xóa:");
            if (u != null && !u.trim().isEmpty()) {
                Object[] options = {"yes", "no"};
                int confirm = JOptionPane.showOptionDialog(this, "Bạn có chắn chắn muốn xóa tài khoản " + u + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (confirm == 0) {
                    String res = sendCommand("DELETE|" + u);
                    if ("SUCCESS".equals(res)) {
                        JOptionPane.showMessageDialog(this, "Xóa thành công");
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa thất bại");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Đã bỏ qua");
                }
            }
        });

        // 7. Nút Exit
        btnExit.addActionListener(e -> {
            boolean sent = false;
            // Nếu đã kết nối thì gửi trực tiếp
            if (socket != null && out != null) {
                try {
                    out.println("EXIT");
                    sent = true;
                } catch (Exception ex) {
                    // Bỏ qua lỗi
                }
            }
            
            // Nếu chưa kết nối, thử mở một kết nối một lần (one-off) để gửi lệnh tắt Server
            if (!sent) {
                try (Socket tempSocket = new Socket(SERVER_IP, SERVER_PORT);
                     PrintWriter tempOut = new PrintWriter(tempSocket.getOutputStream(), true)) {
                    tempOut.println("EXIT");
                } catch (Exception ex) {
                    // Server có thể đã tắt hoặc chưa bật, bỏ qua lỗi này
                }
            }
            
            System.exit(0);
        });
    }

    private void connectToServer() {
        btnConnect.setEnabled(false);
        lblStatus.setText("status: Đang kết nối...");
        lblStatus.setForeground(Color.GRAY); // Đổi thành màu Xám thay vì Cam

        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            private String errorMessage = "";

            @Override
            protected Boolean doInBackground() {
                try {
                    socket = new Socket(SERVER_IP, SERVER_PORT);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    out = new PrintWriter(socket.getOutputStream(), true);
                    return true;
                } catch (IOException ex) {
                    errorMessage = ex.getMessage();
                    return false;
                }
            }

            @Override
            protected void done() {
                try {
                    boolean success = get();
                    if (success) {
                        lblStatus.setText("status: Đã kết nối");
                        lblStatus.setForeground(Color.BLACK); // Đổi thông báo trạng thái connect thành màu đen thay cho Xanh lá
                        JOptionPane.showMessageDialog(ClientGUI.this, "Kết nối lên Server thành công!");
                    } else {
                        lblStatus.setText("status: Chưa kết nối");
                        lblStatus.setForeground(Color.BLACK); // Màu đen thay cho Đỏ
                        JOptionPane.showMessageDialog(ClientGUI.this, "Kết nối thất bại: " + errorMessage);
                        btnConnect.setEnabled(true);
                    }
                } catch (Exception ex) {
                    lblStatus.setText("status: Chưa kết nối");
                    lblStatus.setForeground(Color.BLACK);
                    btnConnect.setEnabled(true);
                }
            }
        };
        worker.execute();
    }

    private String sendCommand(String cmd) {
        if (socket == null || out == null || in == null) {
            JOptionPane.showMessageDialog(this, "Chưa kết nối tới Server, hãy ấn Connect trước.");
            return null;
        }
        try {
            out.println(cmd);
            return in.readLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi giao tiếp mạng: " + ex.getMessage());
            return null;
        }
    }

    // Cài đặt logic giao diện hiển thị button theo trạng thái của Checkbox
    private void updateUIMode(boolean isManageMode) {
        // Trạng thái không chọn: Chỉ hiển thị nút Login
        // Trạng thái được chọn: Ẩn nút Login. Hiển thị các nút Create, Add new, Edit, Delete
        btnLogin.setVisible(!isManageMode);
        
        btnCreate.setVisible(isManageMode);
        btnAddNew.setVisible(isManageMode);
        btnEdit.setVisible(isManageMode);
        btnDelete.setVisible(isManageMode);
        
        // Cập nhật lại giao diện (vì thay đổi trạng thái visible)
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        // Áp dụng Look & Feel bản địa của System để tăng độ mượt mà hiện đại
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            new ClientGUI().setVisible(true);
        });
    }
}