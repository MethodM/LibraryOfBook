package org.example.dto;

import java.time.LocalDateTime;

public class ApiErrorDTO {

  private LocalDateTime timestamp;
  private int status;
  private String message;

  public ApiErrorDTO(String exMessage) {

  }
}