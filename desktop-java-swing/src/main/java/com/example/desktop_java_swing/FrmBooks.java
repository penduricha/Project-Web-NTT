package com.example.desktop_java_swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.example.desktop_java_swing.model.Book;
import com.example.desktop_java_swing.service.impl.BookServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class FrmBooks extends JFrame implements ActionListener {

    private final JTextField txtBookID, txtBookName, txtAuthor;
    private final JSpinner quantity;
    private final JButton btnAdd, btnUpdate, btnDelete, btnReset;
    private final JComboBox<String> cbGenre;
    private final JTable table;
    private final DefaultTableModel tableModel;

    private final BookServiceImpl bookService;

    public FrmBooks(BookServiceImpl bookService) {

        //set Service
        this.bookService = bookService;

        setTitle("Book management");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- North Panel: Form nhập ---
        JPanel pnlInput = new JPanel(new GridLayout(5, 2, 10, 10));
        pnlInput.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        pnlInput.add(new JLabel("Id (Input number):"));
        pnlInput.add(txtBookID = new JTextField());
        setFieldInputTextNumber(txtBookID);

        pnlInput.add(txtBookID);
        //txtBookID.setEditable(false);

        pnlInput.add(new JLabel("Title:"));
        pnlInput.add(txtBookName = new JTextField());
        pnlInput.add(new JLabel("Author:"));
        pnlInput.add(txtAuthor = new JTextField());
        pnlInput.add(new JLabel("Genre:"));
        pnlInput.add(cbGenre = new JComboBox<>(new String[]{"Fiction", "Non-fiction", "Comedy"}));
        pnlInput.add(new JLabel("Quantity:"));

        // Cấu hình JSpinner > 0
        quantity = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        setSpinnerInputTextNumber(quantity);
        pnlInput.add(quantity);

        add(pnlInput, BorderLayout.NORTH);

        // --- Center Panel: Table ---
        String[] columns = {"ID", "Title", "Author", "Category", "Quantity"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        add(new JScrollPane(table), BorderLayout.CENTER);

        //get list
        getListFromDatabase();

        // --- South Panel: Buttons ---
        JPanel pnlFooter = new JPanel();
        pnlFooter.add(btnAdd = new JButton("Add"));
        pnlFooter.add(btnUpdate = new JButton("Update"));
        pnlFooter.add(btnDelete = new JButton("Delete"));
        pnlFooter.add(btnReset = new JButton("Reset"));

        btnAdd.addActionListener(this);
        btnDelete.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnReset.addActionListener(this);
        table.getSelectionModel().addListSelectionListener(e -> {
            // Kiểm tra để tránh việc sự kiện chạy 2 lần khi chọn dòng
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    fillFormFromTable(selectedRow);
                }
            }
        });
        add(pnlFooter, BorderLayout.SOUTH);
    }

    private void setFieldInputTextNumber(JTextField value) {
        ((AbstractDocument) value.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string != null && string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text != null && text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    private void setSpinnerInputTextNumber(JSpinner value) {
        JFormattedTextField tf = ((JSpinner.DefaultEditor) value.getEditor()).getTextField();
        ((AbstractDocument) tf.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string != null && string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text != null && text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    private void resetGUI() {
        txtBookID.setText(null);
        txtBookName.setText(null);
        txtAuthor.setText(null);
        cbGenre.setSelectedIndex(0);
        quantity.setValue(1);
        getListFromDatabase();
    }

    private void fillFormFromTable(int row) {
        // Lấy dữ liệu từ Model theo vị trí hàng và cột
        String bookID = tableModel.getValueAt(row, 0).toString();
        String name = tableModel.getValueAt(row, 1).toString();
        String author = tableModel.getValueAt(row, 2).toString();
        String genre = tableModel.getValueAt(row, 3).toString();
        int qty = Integer.parseInt(tableModel.getValueAt(row, 4).toString());

        // Đưa dữ liệu lên các field
        txtBookID.setText(bookID);
        txtBookName.setText(name);
        txtAuthor.setText(author);
        cbGenre.setSelectedItem(genre);
        quantity.setValue(qty);
    }

    private void getListFromDatabase() {
        tableModel.setRowCount(0);
        List<Book> bookList = bookService.getAllBooks();
        for(Book book: bookList) {
            Long bookId = book.getBookId();
            String title = book.getTitle();
            String author = book.getAuthor();
            String category = book.getCategory();
            int qty = book.getQuantity();
            tableModel.addRow(new Object[]{bookId, title, author, category, qty});
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            Long bookId = Long.valueOf(txtBookID.getText());
            String title = txtBookName.getText();
            String author = txtAuthor.getText();
            String category = (String) cbGenre.getSelectedItem();
            int qty = (int) quantity.getValue();

            Book bookToAdd = new Book();
            bookToAdd.setBookId(bookId);
            bookToAdd.setTitle(title);
            bookToAdd.setAuthor(author);
            bookToAdd.setCategory(category);
            bookToAdd.setQuantity(qty);

            if (bookService.addBook(bookToAdd)) {
                resetGUI();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Please check your input fields",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }

        if(e.getSource() == btnUpdate) {
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1)
            {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to update this book?",
                        "Confirm update",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    String bookIdStr = tableModel.getValueAt(selectedRow, 0).toString();
                    Long bookId = Long.valueOf(bookIdStr);

                    String title = txtBookName.getText();
                    String author = txtAuthor.getText();
                    String category = (String) cbGenre.getSelectedItem();
                    int qty = (int) quantity.getValue();

                    Book bookToUpdate = new Book();
                    bookToUpdate.setBookId(bookId);
                    bookToUpdate.setTitle(title);
                    bookToUpdate.setAuthor(author);
                    bookToUpdate.setCategory(category);
                    bookToUpdate.setQuantity(qty);

                    if(bookService.updateBook(bookToUpdate)) {
                        resetGUI();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row");
            }
        }

        if(e.getSource() == btnDelete) {
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1)
            {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to delete this book?",
                        "Confirm delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    String bookIdStr = tableModel.getValueAt(selectedRow, 0).toString();
                    Long bookId = Long.valueOf(bookIdStr);
                    if(bookService.deleteBookByBookId(bookId)) {
                        resetGUI();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row");
            }

        }

        if(e.getSource() == btnReset) {
            resetGUI();
        }
    }
}