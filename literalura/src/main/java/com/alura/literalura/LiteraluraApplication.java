package com.alura.literalura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.alura.literalura.controller.BookController;

@SpringBootApplication
public class LiteraluraApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(LiteraluraApplication.class, args);
		BookController bookController = context.getBean(BookController.class);
		bookController.start(); // Iniciar el controlador de consola
	}
}