package org.example;

import org.example.controller.LivroController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example"})
public class Main {

  public static void main(String[] args) {

    System.out.println("Olá Mundo!");
    SpringApplication.run(Main.class, args);
    System.out.println("Teste aplicação rodando em localhost:8080" + LivroController.class);

  }
}