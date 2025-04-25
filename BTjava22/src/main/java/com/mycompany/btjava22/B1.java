/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.btjava22;

/**
 *
 * @author PC
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class B1 extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton signUpButton, cancelButton;

    public B1() {
        setTitle("Đăng ký tài khoản");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Tên đăng nhập:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Mật khẩu:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Xác nhận mật khẩu:"));
        confirmPasswordField = new JPasswordField();
        add(confirmPasswordField);

        signUpButton = new JButton("Đăng ký");
        cancelButton = new JButton("Hủy");

        signUpButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Đăng ký thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        cancelButton.addActionListener(e -> System.exit(0));

        add(signUpButton);
        add(cancelButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new B1().setVisible(true));
    }
}

