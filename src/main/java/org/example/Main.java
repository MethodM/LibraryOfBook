package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example"})
public class Main {

  public static void main(String[] args) {

    System.out.println("Hello World!");
    SpringApplication.run(Main.class, args);
    System.out.println("Teste aplicação rodando em localhost:8080" + LivroController.class);

  }
}