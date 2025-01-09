package com.alura.literalura.util;

import com.alura.literalura.model.Book;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ApiClient {
    private final RestTemplate restTemplate;

    public ApiClient() {
        this.restTemplate = new RestTemplate();
    }

    public Book fetchBookFromApi(String title) {
        String url = "https://gutendex.com/books?search=" + title;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("results")) {
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
            if (!results.isEmpty()) {
                Map<String, Object> bookData = results.get(0);
                Book book = new Book();
                book.setTitle((String) bookData.get("title"));
                book.setAuthor((String) bookData.get("author")); // Asegúrate de que el campo sea correcto
                book.setLanguage((String) bookData.get("language")); // Asegúrate de que el campo sea correcto

                // Manejo del campo "downloads"
                Object downloadsObj = bookData.get("downloads");
                if (downloadsObj instanceof Integer) {
                    book.setDownloads((Integer) downloadsObj);
                } else {
                    book.setDownloads(0); // O cualquier valor predeterminado que desees
                }

                // Obtener información del autor
                List<Map<String, Object>> authors = (List<Map<String, Object>>) bookData.get("authors");
                if (!authors.isEmpty()) {
                    Map<String, Object> authorData = authors.get(0);
                    String birthDateStr = (String) authorData.get("birth_date");
                    String deathDateStr = (String) authorData.get("death_date");

                    // Convertir las fechas de String a LocalDate
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    if (birthDateStr != null) {
                        LocalDate birthDate = LocalDate.parse(birthDateStr, formatter);
                        book.setBirthDate(birthDate);
                    }
                    if (deathDateStr != null) {
                        LocalDate deathDate = LocalDate.parse(deathDateStr, formatter);
                        book.setDeathDate(deathDate);
                    }
                }

                return book;
            }
        }
        return null;
    }
}