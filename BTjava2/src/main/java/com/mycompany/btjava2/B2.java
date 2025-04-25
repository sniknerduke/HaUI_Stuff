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

public class B2 extends JFrame {
    private JTextField nameField, idField, birthField, ethnicityField;
    private JRadioButton maleRadio, femaleRadio;
    private JCheckBox englishCheck, frenchCheck;
    private JTextArea displayArea;
    private JButton enterButton, addButton, closeButton;

    public B2() {
        setTitle("Student Form");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        add(new JLabel("Họ và Tên:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Mã SV:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Ngày sinh:"));
        birthField = new JTextField();
        add(birthField);

        add(new JLabel("Dân tộc:"));
        ethnicityField = new JTextField();
        add(ethnicityField);

        add(new JLabel("Giới tính:"));
        maleRadio = new JRadioButton("Nam");
        femaleRadio = new JRadioButton("Nữ");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        add(genderPanel);

        add(new JLabel("Ngoại ngữ:"));
        englishCheck = new JCheckBox("Tiếng Anh");
        frenchCheck = new JCheckBox("Tiếng Pháp");
        JPanel languagePanel = new JPanel();
        languagePanel.add(englishCheck);
        languagePanel.add(frenchCheck);
        add(languagePanel);

        displayArea = new JTextArea(5, 20);
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea));

        enterButton = new JButton("Nhập");
        addButton = new JButton("Thêm");
        closeButton = new JButton("Đóng");

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String gender = maleRadio.isSelected() ? "Nam" : "Nữ";
                String languages = "";
                if (englishCheck.isSelected()) languages += " Tiếng Anh";
                if (frenchCheck.isSelected()) languages += " Tiếng Pháp";

                displayArea.setText("Họ và Tên: " + nameField.getText() + "\n" +
                        "Mã SV: " + idField.getText() + "\n" +
                        "Ngày sinh: " + birthField.getText() + "\n" +
                        "Dân tộc: " + ethnicityField.getText() + "\n" +
                        "Giới tính: " + gender + "\n" +
                        "Ngoại ngữ:" + languages);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                idField.setText("");
                birthField.setText("");
                ethnicityField.setText("");
                maleRadio.setSelected(false);
                femaleRadio.setSelected(false);
                englishCheck.setSelected(false);
                frenchCheck.setSelected(false);
                nameField.requestFocus();
            }
        });

        closeButton.addActionListener(e -> System.exit(0));

        add(enterButton);
        add(addButton);
        add(closeButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new B2().setVisible(true));
    }
}
