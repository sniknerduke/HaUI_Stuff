/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.btjava4;

/**
 *
 * @author PC
 */
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Product implements Serializable {
    private String productId, productName, description;
    private double unitPrice;
    private int quantity;

    public Product(String productId, String productName, String description, double unitPrice, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getDescription() { return description; }
    public double getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
}

class Category implements Serializable {
    private String cateId, cateName;
    private ArrayList<Product> productList = new ArrayList<>();

    public Category(String cateId, String cateName) {
        this.cateId = cateId;
        this.cateName = cateName;
    }

    public String getCateId() { return cateId; }
    public String getCateName() { return cateName; }
    public boolean addProduct(Product p) { return productList.add(p); }
    public ArrayList<Product> getProductList() { return productList; }
}

class MyProcessFile {
    public static void saveData(Object data, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Object openData(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return in.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

public class B1 extends JFrame {
    private JTextField txtCategoryId, txtCategoryName;
    private DefaultListModel<String> categoryModel;
    private JList<String> categoryList;
    private JButton btnAddCategory, btnSave, btnLoad;

    public B1() {
        setTitle("Quản lý sản phẩm");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        txtCategoryId = new JTextField(10);
        txtCategoryName = new JTextField(10);
        categoryModel = new DefaultListModel<>();
        categoryList = new JList<>(categoryModel);
        btnAddCategory = new JButton("Thêm danh mục");
        btnSave = new JButton("Lưu dữ liệu");
        btnLoad = new JButton("Tải dữ liệu");

        add(new JLabel("Mã danh mục:"));
        add(txtCategoryId);
        add(new JLabel("Tên danh mục:"));
        add(txtCategoryName);
        add(new JScrollPane(categoryList));
        add(btnAddCategory);
        add(btnSave);
        add(btnLoad);

        btnAddCategory.addActionListener(e -> {
            String category = txtCategoryId.getText() + " - " + txtCategoryName.getText();
            categoryModel.addElement(category);
        });

        btnSave.addActionListener(e -> MyProcessFile.saveData(categoryModel, "categories.dat"));
        btnLoad.addActionListener(e -> {
            DefaultListModel<String> loadedModel = (DefaultListModel<String>) MyProcessFile.openData("categories.dat");
            if (loadedModel != null) {
                categoryModel.clear();
                for (int i = 0; i < loadedModel.size(); i++) {
                    categoryModel.addElement(loadedModel.get(i));
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new B1().setVisible(true));
    }
}
