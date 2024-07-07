package com.petstation.petstation.dtos;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class ErrorDTO {

    private final int statusCode;
    private final String message;
    private final String description;

    public ErrorDTO(int statusCode, String message,String description) {
        this.statusCode = statusCode;
        this.message = message;
        this.description = description;
    }
}
