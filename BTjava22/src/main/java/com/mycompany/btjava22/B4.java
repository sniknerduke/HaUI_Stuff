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

public class B4 extends JFrame implements ActionListener {
    private JTextField display;
    private String operator;
    private double num1, num2, result;
    private boolean startNewNumber = true;

    public B4() {
        setTitle("Máy tính Windows");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tạo màn hình hiển thị
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Tạo bảng chứa các nút số và phép toán
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789".contains(command)) {
            if (startNewNumber) {
                display.setText(command);
                startNewNumber = false;
            } else {
                display.setText(display.getText() + command);
            }
        } else if ("+-*/".contains(command)) {
            operator = command;
            num1 = Double.parseDouble(display.getText());
            startNewNumber = true;
        } else if (command.equals("=")) {
            num2 = Double.parseDouble(display.getText());
            switch (operator) {
                case "+": result = num1 + num2; break;
                case "-": result = num1 - num2; break;
                case "*": result = num1 * num2; break;
                case "/": result = num2 != 0 ? num1 / num2 : 0; break;
            }
            display.setText(String.valueOf(result));
            startNewNumber = true;
        } else if (command.equals("C")) {
            display.setText("0");
            num1 = num2 = result = 0;
            startNewNumber = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new B4().setVisible(true));
    }
}

