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
import java.awt.event.*;

public class B1 extends JFrame {
    private JCheckBoxMenuItem confirmExitMenuItem;
    private JPopupMenu popupMenu;
    private JMenuItem redMenuItem, blueMenuItem, exitPopupMenuItem;

    public B1() {
        setTitle("Bài 1 - Menu & Toolbar");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tạo MenuBar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu settingMenu = new JMenu("Setting");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        confirmExitMenuItem = new JCheckBoxMenuItem("Confirm Exit");

        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        settingMenu.add(confirmExitMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(settingMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        // Toolbar
        JToolBar toolBar = new JToolBar();
        JButton saveButton = new JButton("Save");
        JButton exitButton = new JButton("Exit");

        toolBar.add(saveButton);
        toolBar.add(exitButton);
        add(toolBar, BorderLayout.NORTH);

        // Popup Menu
        popupMenu = new JPopupMenu();
        redMenuItem = new JMenuItem("Red");
        blueMenuItem = new JMenuItem("Blue");
        exitPopupMenuItem = new JMenuItem("Exit");

        popupMenu.add(redMenuItem);
        popupMenu.add(blueMenuItem);
        popupMenu.addSeparator();
        popupMenu.add(exitPopupMenuItem);

        // Sự kiện Save
        saveMenuItem.addActionListener(e -> showSaveDialog());
        saveButton.addActionListener(e -> showSaveDialog());

        // Sự kiện Exit
        exitMenuItem.addActionListener(e -> exitApplication());
        exitButton.addActionListener(e -> exitApplication());
        exitPopupMenuItem.addActionListener(e -> exitApplication());

        // Sự kiện đổi màu chữ
        redMenuItem.addActionListener(e -> getContentPane().setBackground(Color.RED));
        blueMenuItem.addActionListener(e -> getContentPane().setBackground(Color.BLUE));

        // Hiển thị Popup Menu khi nhấn chuột phải
        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private void showSaveDialog() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(this, "Đã lưu: " + fileChooser.getSelectedFile().getName());
        }
    }

    private void exitApplication() {
        if (confirmExitMenuItem.isSelected()) {
            int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new B1().setVisible(true));
    }
}
