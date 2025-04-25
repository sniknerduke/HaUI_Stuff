/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.btjava3;

/**
 *
 * @author PC
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class B2 extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JTabbedPane tabbedPane;

    public B2() {
        setTitle("Bài 2 - Login & Main Form");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Login", createLoginPanel());
        tabbedPane.addTab("Main Form", createMainFormPanel());
        tabbedPane.setEnabledAt(1, false); // Khóa Main Form khi chưa đăng nhập

        add(tabbedPane);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(loginButton);

        loginButton.addActionListener(e -> validateLogin());

        return panel;
    }

    private JPanel createMainFormPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Chào mừng bạn đến với Main Form!"));
        return panel;
    }

    private void validateLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if ("admin".equals(username) && "123".equals(password)) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            tabbedPane.setEnabledAt(1, true);
            tabbedPane.setSelectedIndex(1);
        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new B2().setVisible(true));
    }
}
