package com.alura.literalura.service;

import com.alura.literalura.model.Book;
import com.alura.literalura.repository.BookRepository;
import com.alura.literalura.util.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ApiClient apiClient;

    public Book searchBookByTitle(String title) {
        Optional<Book> existingBook = bookRepository.findByTitle(title);
        if (existingBook.isPresent()) {
            System.out.println("El libro ya está registrado en la base de datos.");
            return existingBook.get();
        }

        Book bookFromApi = apiClient.fetchBookFromApi(title);
        if (bookFromApi != null) {
            return bookRepository.save(bookFromApi);
        } else {
            System.out.println("El libro no fue encontrado.");
            return null;
        }
    }

    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    public List<String> listAllAuthors() {
        return bookRepository.findAll().stream()
                .map(Book::getAuthor)
                .distinct()
                .toList();
    }

    public List<Book> listBooksByLanguage(String language) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getLanguage().equalsIgnoreCase(language))
                .toList();
    }

    public List<String> listLivingAuthors(int year) {
        return bookRepository.findAll().stream()
                .filter(book -> {
                    LocalDate birthDate = book.getBirthDate();
                    LocalDate deathDate = book.getDeathDate();
                    return (birthDate != null && birthDate.getYear() <= year) &&
                            (deathDate == null || deathDate.getYear() >= year);
                })
                .map(Book::getAuthor)
                .distinct()
                .toList();
    }
}