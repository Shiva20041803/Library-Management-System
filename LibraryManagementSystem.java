package librarymanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LibraryManagementSystem extends JFrame {
    private JTextField userIdField, nameField, emailField, passwordField;
    private JTextField bookIdField, bookTitleField, bookAuthorField, bookQuantityField;
    private JTable userTable, bookTable;
    private JTextField searchField;
    private JTextArea displayArea;

    public LibraryManagementSystem() {
        setTitle("Library Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // User Management Panel
        JPanel userPanel = new JPanel(new GridBagLayout());
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // User Registration
        gbc.gridx = 0; gbc.gridy = 0;
        userPanel.add(new JLabel("User ID:"), gbc);
        userIdField = new JTextField(20);
        gbc.gridx = 1;
        userPanel.add(userIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        userPanel.add(new JLabel("Name:"), gbc);
        nameField = new JTextField(20);
        gbc.gridx = 1;
        userPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        userPanel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        userPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        userPanel.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        userPanel.add(passwordField, gbc);

        JButton registerButton = new JButton("Register User");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    User user = new User(userIdField.getText(), nameField.getText(), emailField.getText(), passwordField.getText());
                    User.registerUser(user);
                    displayArea.setText("User registered successfully!");
                    refreshUserTable();
                } catch (IOException ex) {
                    displayArea.setText("Error registering user.");
                }
            }
        });
        gbc.gridx = 1; gbc.gridy = 4;
        userPanel.add(registerButton, gbc);

        // User Table
        userTable = new JTable();
        JScrollPane userTableScrollPane = new JScrollPane(userTable);
        userTableScrollPane.setPreferredSize(new Dimension(500, 200));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.BOTH;
        userPanel.add(userTableScrollPane, gbc);

        JButton viewUsersButton = new JButton("View Users");
        viewUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshUserTable();
            }
        });
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        userPanel.add(viewUsersButton, gbc);

        tabbedPane.addTab("User Management", userPanel);

        // Book Management Panel
        JPanel bookPanel = new JPanel(new GridBagLayout());
        bookPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gbc.gridx = 0; gbc.gridy = 0;
        bookPanel.add(new JLabel("Book ID:"), gbc);
        bookIdField = new JTextField(20);
        gbc.gridx = 1;
        bookPanel.add(bookIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        bookPanel.add(new JLabel("Title:"), gbc);
        bookTitleField = new JTextField(20);
        gbc.gridx = 1;
        bookPanel.add(bookTitleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        bookPanel.add(new JLabel("Author:"), gbc);
        bookAuthorField = new JTextField(20);
        gbc.gridx = 1;
        bookPanel.add(bookAuthorField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        bookPanel.add(new JLabel("Quantity:"), gbc);
        bookQuantityField = new JTextField(20);
        gbc.gridx = 1;
        bookPanel.add(bookQuantityField, gbc);

        JButton addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Book book = new Book(bookIdField.getText(), bookTitleField.getText(), bookAuthorField.getText(), Integer.parseInt(bookQuantityField.getText()));
                    Book.addBook(book);
                    displayArea.setText("Book added successfully!");
                    refreshBookTable();
                } catch (IOException ex) {
                    displayArea.setText("Error adding book.");
                }
            }
        });
        gbc.gridx = 1; gbc.gridy = 4;
        bookPanel.add(addBookButton, gbc);

        // Book Table
        bookTable = new JTable();
        JScrollPane bookTableScrollPane = new JScrollPane(bookTable);
        bookTableScrollPane.setPreferredSize(new Dimension(500, 200));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.BOTH;
        bookPanel.add(bookTableScrollPane, gbc);

        JButton viewBooksButton = new JButton("View Books");
        viewBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshBookTable();
            }
        });
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        bookPanel.add(viewBooksButton, gbc);

        tabbedPane.addTab("Book Management", bookPanel);

        // Borrow/Return/Search Panel
        JPanel borrowReturnPanel = new JPanel(new GridBagLayout());
        borrowReturnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gbc.gridx = 0; gbc.gridy = 0;
        borrowReturnPanel.add(new JLabel("Search:"), gbc);
        searchField = new JTextField(20);
        gbc.gridx = 1;
        borrowReturnPanel.add(searchField, gbc);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        gbc.gridx = 2; gbc.gridy = 0;
        borrowReturnPanel.add(searchButton, gbc);

        JButton borrowBookButton = new JButton("Borrow Book");
        borrowBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrowBook();
            }
        });
        gbc.gridx = 0; gbc.gridy = 1;
        borrowReturnPanel.add(borrowBookButton, gbc);

        JButton returnBookButton = new JButton("Return Book");
        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });
        gbc.gridx = 1; gbc.gridy = 1;
        borrowReturnPanel.add(returnBookButton, gbc);

        tabbedPane.addTab("Borrow/Return/Search", borrowReturnPanel);

        // Display Area
        displayArea = new JTextArea(5, 50);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setEditable(false);
        JScrollPane displayScrollPane = new JScrollPane(displayArea);

        add(tabbedPane, BorderLayout.CENTER);
        add(displayScrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void refreshUserTable() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"User ID", "Name", "Email"}, 0);
        for (User user : User.getAllUsers()) {
            model.addRow(new Object[]{user.getId(), user.getName(), user.getEmail()});
        }
        userTable.setModel(model);
    }

    private void refreshBookTable() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Book ID", "Title", "Author", "Quantity"}, 0);
        for (Book book : Book.getAllBooks()) {
            model.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor(), book.getQuantity()});
        }
        bookTable.setModel(model);
    }

    private void search() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            displayArea.setText("Please enter something to search.");
            return;
        }

        StringBuilder result = new StringBuilder("Search Results:\n");
        for (User user : User.getAllUsers()) {
            if (user.getId().contains(searchText) || user.getName().contains(searchText) || user.getEmail().contains(searchText)) {
                result.append("User: ").append(user.getName()).append(", ID: ").append(user.getId()).append("\n");
            }
        }

        for (Book book : Book.getAllBooks()) {
            if (book.getId().contains(searchText) || book.getTitle().contains(searchText) || book.getAuthor().contains(searchText)) {
                result.append("Book: ").append(book.getTitle()).append(", ID: ").append(book.getId()).append("\n");
            }
        }

        if (result.toString().equals("Search Results:\n")) {
            displayArea.setText("No results found.");
        } else {
            displayArea.setText(result.toString());
        }
    }

    private void borrowBook() {
        String bookId = JOptionPane.showInputDialog(this, "Enter Book ID to borrow:");
        if (bookId != null && !bookId.trim().isEmpty()) {
            // Implement borrow logic here
            displayArea.setText("Book borrowed successfully!");
        } else {
            displayArea.setText("Borrowing canceled.");
        }
    }

    private void returnBook() {
        String bookId = JOptionPane.showInputDialog(this, "Enter Book ID to return:");
        if (bookId != null && !bookId.trim().isEmpty()) {
            // Implement return logic here
            displayArea.setText("Book returned successfully!");
        } else {
            displayArea.setText("Returning canceled.");
        }
    }

    public static void main(String[] args) {
        new LibraryManagementSystem();
    }
}
