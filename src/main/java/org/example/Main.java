package org.example;//package org.example;

import org.example.Library;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВыберите действие: ");
            System.out.println("1. Добавить книгу");
            System.out.println("2. Посмотреть все книги");
            System.out.println("3. Обновить книгу");
            System.out.println("4. Удалить книгу");
            System.out.println("5. Выйти");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            switch (choice) {
                case 1:
                    System.out.print("Введите название книги: ");
                    String title = scanner.nextLine();
                    System.out.print("Введите автора книги: ");
                    String author = scanner.nextLine();
                    System.out.print("Введите год издания: ");
                    int year = scanner.nextInt();
                    library.addBook(title, author, year);
                    break;

                case 2:
                    List<Book> books = library.viewBooks();
                    if (books.isEmpty()) {
                        System.out.println("Нет книг в библиотеке.");
                    } else {
                        System.out.println("Список книг:");
                        for (Book book : books) {
                            System.out.println(book);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Введите ID книги для обновления: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Очистка буфера
                    System.out.print("Введите новое название книги: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Введите нового автора книги: ");
                    String newAuthor = scanner.nextLine();
                    System.out.print("Введите новый год издания: ");
                    int newYear = scanner.nextInt();
                    library.updateBook(updateId, newTitle, newAuthor, newYear);
                    break;

                case 4:
                    System.out.print("Введите ID книги для удаления: ");
                    int deleteId = scanner.nextInt();
                    library.deleteBook(deleteId);
                    break;

                case 5:
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }
}