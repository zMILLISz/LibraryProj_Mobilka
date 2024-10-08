package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    public static final String url = "jdbc:postgresql://localhost:9000/Books";
    public static final String DB_DRIVER = "org.postgresql.Driver";

    private Connection connect() {
        String user = "postgres";
        String password = "1qaz!QAZ";
        Connection conn = null;
        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public void addBook(String title, String author, int year) {
        String sql = "INSERT INTO \"books\" (title, author, year) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, year);
            pstmt.executeUpdate();
            System.out.println("Книга добавлена: " + title);
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении книги: " + e.getMessage());
        }
    }

    public List<Book> viewBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM \"books\"";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt(1); //
                String title = rs.getString(2);
                String author = rs.getString(3);
                int year = rs.getInt(4);
                Book book = new Book(title, author, String.valueOf(year));
                book.setId(id);
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при получении книг: " + e.getMessage());
        }
        return books;
    }

    public void updateBook(int id, String title, String author, int year) {
        String sql = "UPDATE \"books\" SET title = ?, author = ?, year = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, year);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Книга обновлена: ID " + id);
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении книги: " + e.getMessage());
        }
    }

    public void deleteBook(int id) {
        String sql = "DELETE FROM \"books\" WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Книга удалена: ID " + id);
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении книги: " + e.getMessage());
        }
    }
}
