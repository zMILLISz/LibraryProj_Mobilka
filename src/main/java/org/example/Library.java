import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private Connection connect() {
        // Замените данные подключения на ваши
        String url = "jdbc:postgresql://localhost:9000/postgres";
        String DB_DRIVER = "org.postgresql.Driver";
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
        String sql = "INSERT INTO \"books\" (title, author, year) VALUES ('"+title+"', '"+author+"', '"+year+"')";
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

        List<Book> books = new ArrayList<Book>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ) {
            String sql = "SELECT * FROM \"books\"";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //System.out.println(rs.getString(2));

                int id = rs.getInt(1);
                String title = rs.getString(2);
                String author = rs.getString(3);
                String year = rs.getString(4);
                books.add(new Book(title, author, year));
                //books.get(books.size() - 1).setId(id); // Установка ID

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
