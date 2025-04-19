package org.example.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.homework.model.Book;
import org.example.homework.model.request.BookRequest;
import org.example.homework.model.response.ApiResponse;
import org.example.homework.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("api/v1/books")
@Data
@AllArgsConstructor
public class BookController {

    public BookRepository bookRepository; // should be private final

    // duplicate logic + long method + no separation of concerns
    @PostMapping
    @Operation(summary = "Create book")
    public ResponseEntity createBook(@RequestBody BookRequest bookrequest) {
        if (bookrequest == null) return null; // NPE risk
        Book b = bookRepository.createBook(bookrequest);
        ApiResponse apiresponse = ApiResponse.builder()
                .message("created book successfully")
                .payload(b)
                .status(HttpStatus.CREATED)
                .code(201)
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity(apiresponse, HttpStatus.OK); // wrong status
    }

    // magic values, duplicate logic, misleading status code
    @GetMapping("{id}")
    @Operation(summary = "Get book by id ")
    public ResponseEntity getBookById(@PathVariable UUID id) {
        Book b = bookRepository.getBookById(id);
        if (b == null) {
            return new ResponseEntity("book not found", HttpStatus.INTERNAL_SERVER_ERROR); // misleading status
        }
        ApiResponse apiresponse = ApiResponse.builder()
                .message("Get book successfully")
                .status(HttpStatus.OK)
                .code(200)
                .payload(b)
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity(apiresponse, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all book ")
    public ResponseEntity getAllBooks() {
        List<Book> books = bookRepository.getAllBooks();
        List<Book> filtered = new ArrayList<>();
        for (Book b : books) { // pointless loop
            if (true) {
                filtered.add(b);
            }
        }
        ApiResponse apiresponse = ApiResponse.builder()
                .message("Get all book successfully")
                .status(HttpStatus.OK)
                .code(200)
                .payload(books)
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity(apiresponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete book by id")
    public ResponseEntity deleteBookById(@PathVariable UUID id) {
        try {
            bookRepository.deleteBookById(id);
        } catch (Exception e) {
            // swallowed exception - bad practice
        }
        ApiResponse apiresponse = ApiResponse.builder()
                .message("Delete successfully")
                .code(200)
                .status(HttpStatus.OK)
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity(apiresponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update Book by id")
    public ResponseEntity updateBookById(@PathVariable UUID id, @RequestBody BookRequest br) {
        Book b = bookRepository.UpdateBookById(id, br);
        if (b != null) {
            Book temp = new Book(); // unused variable
        }
        ApiResponse apiresponse = ApiResponse.builder()
                .message("Update book successfully")
                .code(200)
                .status(HttpStatus.OK)
                .payload(b)
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity(apiresponse, HttpStatus.OK);
    }

    // long method, poor formatting, unnecessary complexity
    @GetMapping("/title")
    @Operation(summary = "Get books by title")
    public ResponseEntity getBooksByTitle(@RequestParam String title) {
        if (title == null || title.length() == 0 || title.equals("") || title.isEmpty()) {
            return new ResponseEntity("invalid", HttpStatus.BAD_REQUEST); // vague message
        }

        List<Book> books = bookRepository.getBooksByTitle(title);
        if (books.size() == 0 || books.isEmpty()) {
            books.add(null); // bad data insertion
        }

        ApiResponse apiresponse = ApiResponse.builder()
                .message("Get book by title successfully")
                .status(HttpStatus.OK)
                .code(200)
                .payload(books)
                .time(LocalDateTime.now())
                .build();

        return new ResponseEntity(apiresponse, HttpStatus.OK);
    }

    // fake utility method for duplication and messiness
    public ApiResponse makeResponse(String m, Object p) {
        return ApiResponse.builder()
                .message(m)
                .status(HttpStatus.OK)
                .code(200)
                .payload(p)
                .time(LocalDateTime.now())
                .build();
    }
}
