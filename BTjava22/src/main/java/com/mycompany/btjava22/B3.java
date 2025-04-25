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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class B3 extends JFrame {
    private JTextField nameField, idField, dobField, majorField;
    private JButton saveButton, resetButton, exitButton;

    public B3() {
        setTitle("Đăng ký thông tin sinh viên");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Họ và tên:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Mã SV:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Ngày sinh:"));
        dobField = new JTextField();
        add(dobField);

        add(new JLabel("Ngành học:"));
        majorField = new JTextField();
        add(majorField);

        saveButton = new JButton("Lưu");
        resetButton = new JButton("Làm lại");
        exitButton = new JButton("Thoát");

        saveButton.addActionListener(e -> {
            if (nameField.getText().isEmpty() || idField.getText().isEmpty() ||
                dobField.getText().isEmpty() || majorField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt", true))) {
                writer.write(nameField.getText() + ", " + idField.getText() + ", " + dobField.getText() + ", " + majorField.getText());
                writer.newLine();
                JOptionPane.showMessageDialog(this, "Lưu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        resetButton.addActionListener(e -> {
            nameField.setText("");
            idField.setText("");
            dobField.setText("");
            majorField.setText("");
        });

        exitButton.addActionListener(e -> System.exit(0));

        add(saveButton);
        add(resetButton);
        add(exitButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new B3().setVisible(true));
    }
}

