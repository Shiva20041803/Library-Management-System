package librarymanagementsystem;

import java.io.*;

public class Library {
    // Method to borrow a book
    public static void borrowBook(String userId, String bookId) throws IOException {
        FileWriter fw = new FileWriter("transactions.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(userId + "," + bookId + "," + "borrowed");
        bw.newLine();
        bw.close();

        // Update the book's available copies
        updateBookCopies(bookId, -1);
    }

    // Method to return a book
    public static void returnBook(String userId, String bookId) throws IOException {
        FileWriter fw = new FileWriter("transactions.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(userId + "," + bookId + "," + "returned");
        bw.newLine();
        bw.close();

        // Update the book's available copies
        updateBookCopies(bookId, 1);
    }

    // Method to update the number of available copies of a book
    private static void updateBookCopies(String bookId, int change) throws IOException {
        File inputFile = new File("books.txt");
        File tempFile = new File("books_temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        boolean bookFound = false;

        while ((line = reader.readLine()) != null) {
            String[] bookData = line.split(",");
            if (bookData[0].equals(bookId)) {
                int availableCopies = Integer.parseInt(bookData[3]) + change;
                writer.write(bookData[0] + "," + bookData[1] + "," + bookData[2] + "," + availableCopies);
                bookFound = true;
            } else {
                writer.write(line);
            }
            writer.newLine();
        }

        writer.close();
        reader.close();

        if (!bookFound) {
            tempFile.delete(); // Delete temp file if book is not found
            throw new IOException("Book ID not found: " + bookId);
        } else {
            // Replace the original file with the updated one
            if (!inputFile.delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(inputFile)) {
                throw new IOException("Could not rename temporary file");
            }
        }
    }
    public static void main(String[] args) {
        // Launch the Library Management System GUI
        LibraryManagementSystem librarySystem = new LibraryManagementSystem();
        librarySystem.setVisible(true);
    }
}
