package com.alura.literalura.controller;

import com.alura.literalura.model.Book;
import com.alura.literalura.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar todos los libros");
            System.out.println("3. Listar todos los autores");
            System.out.println("4. Listar libros por idioma");
            System.out.println("5. Listar autores vivos en un año");
            System.out.println("0. Salir");
            option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1:
                    System.out.print("Ingrese el título del libro: ");
                    String title = scanner.nextLine();
                    Book book = bookService.searchBookByTitle(title);
                    if (book != null) {
                        System.out.println("Libro encontrado: " + book.getTitle() + " de " + book.getAuthor());
                    }
                    break;
                case 2:
                    List<Book> books = bookService.listAllBooks();
                    System.out.println("Libros registrados:");
                    for (Book b : books) {
                        System.out.println(b.getTitle() + " de " + b.getAuthor());
                    }
                    break;
                case 3:
                    List<String> authors = bookService.listAllAuthors();
                    System.out.println("Autores registrados:");
                    for (String author : authors) {
                        System.out.println(author);
                    }
                    break;
                case 4:
                    System.out.print("Ingrese el idioma (ES, EN, FR, PT): ");
                    String language = scanner.nextLine();
                    List<Book> booksByLanguage = bookService.listBooksByLanguage(language);
                    System.out.println("Libros en " + language + ":");
                    for (Book b : booksByLanguage) {
                        System.out.println(b.getTitle() + " de " + b.getAuthor());
                    }
                    break;
                case 5:
                    System.out.print("Ingrese el año: ");
                    int year = scanner.nextInt();
                    List<String> livingAuthors = bookService.listLivingAuthors(year);
                    System.out.println("Autores vivos en " + year + ":");
                    for (String author : livingAuthors) {
                        System.out.println(author);
                    }
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (option != 0);

        scanner.close();
    }
}

