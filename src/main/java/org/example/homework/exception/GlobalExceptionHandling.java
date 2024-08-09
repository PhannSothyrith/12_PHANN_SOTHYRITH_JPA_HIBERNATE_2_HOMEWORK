package org.example.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;
@RestController
@RestControllerAdvice

public class GlobalExceptionHandling {
@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CustomNotFoundException.class})
    public ProblemDetail handlerAllNotFoundException (CustomNotFoundException e ) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    problemDetail.setType(URI.create("about:blank"));
    problemDetail.setTitle("Not Found");
    problemDetail.setStatus(404);
    problemDetail.setDetail(e.getMessage());
    problemDetail.setProperty("timestamp", LocalDateTime.now());
    return  problemDetail;
}
}

