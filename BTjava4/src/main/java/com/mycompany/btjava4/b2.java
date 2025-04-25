/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.btjava4;

/**
 *
 * @author PC
 */
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

class Student {
    private String id, name, address;
    private int age;
    private double gpa;

    public Student(String id, String name, int age, String address, double gpa) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.gpa = gpa;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getAddress() { return address; }
    public double getGpa() { return gpa; }
}

class StudentDao {
    private Connection conn;

    public StudentDao() { conn = DatabaseConnection.getConnection(); }

    public void addStudent(Student s) {
        String sql = "INSERT INTO students (id, name, age, address, gpa) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, s.getId());
            stmt.setString(2, s.getName());
            stmt.setInt(3, s.getAge());
            stmt.setString(4, s.getAddress());
            stmt.setDouble(5, s.getGpa());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(rs.getString("id"), rs.getString("name"),
                        rs.getInt("age"), rs.getString("address"), rs.getDouble("gpa")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return students;
    }
}

public class b2 extends JFrame {
    private JTextField txtId, txtName, txtAge, txtAddress, txtGpa;
    private JTextArea txtArea;
    private StudentDao studentDao;

    public b2() {
        setTitle("Quản lý sinh viên JDBC");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        studentDao = new StudentDao();
        txtId = new JTextField(10);
        txtName = new JTextField(10);
        txtAge = new JTextField(5);
        txtAddress = new JTextField(10);
        txtGpa = new JTextField(5);
        txtArea = new JTextArea(10, 40);
        txtArea.setEditable(false);
        
        JButton btnAdd = new JButton("Thêm");
        JButton btnShow = new JButton("Hiển thị");

        add(new JLabel("ID:")); add(txtId);
        add(new JLabel("Name:")); add(txtName);
        add(new JLabel("Age:")); add(txtAge);
        add(new JLabel("Address:")); add(txtAddress);
        add(new JLabel("GPA:")); add(txtGpa);
        add(btnAdd); add(btnShow);
        add(new JScrollPane(txtArea));

        btnAdd.addActionListener(e -> studentDao.addStudent(new Student(txtId.getText(), txtName.getText(),
                Integer.parseInt(txtAge.getText()), txtAddress.getText(), Double.parseDouble(txtGpa.getText()))));

        btnShow.addActionListener(e -> {
            txtArea.setText(""); studentDao.getAllStudents().forEach(s -> txtArea.append(s.getId() + " - " + s.getName() + " - " + s.getAge() + " - " + s.getGpa() + "\n"));
        });
    }

    public static void main(String[] args) {
        new b2();
    }
}
