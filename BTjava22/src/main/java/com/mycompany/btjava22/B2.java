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

public class B2 extends JFrame {
    private JTextField aField, bField, cField;
    private JTextArea resultArea;
    private JButton solveButton, clearButton, exitButton;

    public B2() {
        setTitle("Giải phương trình bậc 2");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Hệ số a:"));
        aField = new JTextField();
        add(aField);

        add(new JLabel("Hệ số b:"));
        bField = new JTextField();
        add(bField);

        add(new JLabel("Hệ số c:"));
        cField = new JTextField();
        add(cField);

        solveButton = new JButton("Giải");
        clearButton = new JButton("Xóa trắng");
        exitButton = new JButton("Thoát");

        resultArea = new JTextArea(3, 20);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea));

        solveButton.addActionListener(e -> {
            try {
                double a = Double.parseDouble(aField.getText());
                double b = Double.parseDouble(bField.getText());
                double c = Double.parseDouble(cField.getText());

                double delta = b * b - 4 * a * c;
                String result;
                if (delta > 0) {
                    double x1 = (-b + Math.sqrt(delta)) / (2 * a);
                    double x2 = (-b - Math.sqrt(delta)) / (2 * a);
                    result = "Nghiệm x1: " + x1 + "\nNghiệm x2: " + x2;
                } else if (delta == 0) {
                    double x = -b / (2 * a);
                    result = "Nghiệm kép x: " + x;
                } else {
                    result = "Phương trình vô nghiệm!";
                }
                resultArea.setText(result);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            aField.setText("");
            bField.setText("");
            cField.setText("");
            resultArea.setText("");
        });

        exitButton.addActionListener(e -> System.exit(0));

        add(solveButton);
        add(clearButton);
        add(exitButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new B2().setVisible(true));
    }
}

