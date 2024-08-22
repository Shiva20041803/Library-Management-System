package librarymanagementsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private String id;
    private String title;
    private String author;
    private int quantity;

    public Book(String id, String title, String author, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getQuantity() { return quantity; }

    // Add a new book
    public static void addBook(Book book) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt", true))) {
            writer.write(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getQuantity());
            writer.newLine();
        }
    }

    // Retrieve all books
    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    books.add(new Book(parts[0], parts[1], parts[2], Integer.parseInt(parts[3])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Borrow a book
    public static void borrowBook(String bookId) throws IOException {
        List<Book> books = getAllBooks();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
            for (Book book : books) {
                if (book.getId().equals(bookId)) {
                    if (book.getQuantity() > 0) {
                        book.quantity -= 1;
                    } else {
                        throw new IOException("Book not available for borrowing.");
                    }
                }
                writer.write(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getQuantity());
                writer.newLine();
            }
        }
    }

    // Return a book
    public static void returnBook(String bookId) throws IOException {
        List<Book> books = getAllBooks();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
            for (Book book : books) {
                if (book.getId().equals(bookId)) {
                    book.quantity += 1;
                }
                writer.write(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getQuantity());
                writer.newLine();
            }
        }
    }
}
