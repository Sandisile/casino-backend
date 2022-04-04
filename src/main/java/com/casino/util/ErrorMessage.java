package com.casino.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorMessage {
    private Integer status;
    private LocalDate date;
    private String message;
}
