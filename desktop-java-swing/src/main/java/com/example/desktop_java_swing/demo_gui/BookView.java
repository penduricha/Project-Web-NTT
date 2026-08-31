package com.example.desktop_java_swing.demo_gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BookView extends JFrame {
    private JTextField txtBookId, txtBookName, txtAuthor;
    private JButton btnAdd, btnUpdate, btnDelete;
    private JTable table;
    private DefaultTableModel tableModel;

    public BookView() {
        setTitle("Phần mềm Quản lý Thư viện sách - Giao diện Java Swing");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. Khu vực nhập liệu (Input Form Panel)
        JPanel panelInput = new JPanel(new GridLayout(3, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết sách"));

        panelInput.add(new JLabel("Mã sách:"));
        txtBookId = new JTextField();
        panelInput.add(txtBookId);

        panelInput.add(new JLabel("Tên sách:"));
        txtBookName = new JTextField();
        panelInput.add(txtBookName);

        panelInput.add(new JLabel("Tác giả:"));
        txtAuthor = new JTextField();
        panelInput.add(txtAuthor);

        // 2. Khu vực chứa các nút chức năng CRUD (Action Buttons Panel)
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAdd = new JButton("Thêm mới");
        btnUpdate = new JButton("Cập nhật");
        btnDelete = new JButton("Xóa bỏ");

        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);

        // Gom nhóm Panel phía trên
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelInput, BorderLayout.CENTER);
        panelTop.add(panelButtons, BorderLayout.SOUTH);

        // 3. Khu vực hiển thị bảng dữ liệu (Data Table Panel)
        String[] columnNames = {"Mã sách", "Tên sách", "Tác giả"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách sách trong hệ thống"));

        // 4. Bố trí tổng thể cho cửa sổ chính (JFrame)
        setLayout(new BorderLayout(10, 10));
        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Khởi chạy giao diện trên luồng sự kiện đồ họa (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            new BookView().setVisible(true);
        });
    }
}