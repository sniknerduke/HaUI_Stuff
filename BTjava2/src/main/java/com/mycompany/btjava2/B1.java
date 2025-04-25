/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.btjava2;

/**
 *
 * @author PC
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class B1 extends JFrame {
    private JTextField textField;
    private JButton button;
    private JLabel label;
    
    public B1() {
        setTitle("Simple GUI Example");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        textField = new JTextField(20);
        button = new JButton("Click me");
        label = new JLabel("Enter text and press the button");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("You entered: " + textField.getText());
            }
        });

        add(textField);
        add(button);
        add(label);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new B1().setVisible(true);
        });
    }
}

