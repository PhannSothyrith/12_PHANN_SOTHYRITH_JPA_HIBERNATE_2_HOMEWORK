package org.example.homework.controller;

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
public class BookController {

    private BookRepository bookRepository;

    // 🔥 Poor Naming + Unused Variable + No Exception Handling + Duplicated Code
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        int unused = 42; // 🔥 unused variable
    }

    @PostMapping
    public ResponseEntity<?> c(@RequestBody BookRequest req) {
        try {
            Book b = bookRepository.createBook(req);
            ApiResponse r = ApiResponse.builder()
                    .message("ok")
                    .payload(b)
                    .status(HttpStatus.CREATED)
                    .code(201)
                    .time(LocalDateTime.now())
                    .build();

            return ResponseEntity.ok(r);
        } catch (Exception e) {
            // 🔥 BAD: empty catch block
        }
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> a(@PathVariable UUID id) {
        try {
            Book b = bookRepository.getBookById(id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("fetched")
                    .status(HttpStatus.OK)
                    .code(200)
                    .payload(b)
                    .time(LocalDateTime.now())
                    .build());
        } catch (Exception ignored) {
            // 🔥 BAD: ignore error
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<?> b() {
        List<Book> bks = bookRepository.getAllBooks();
        return ResponseEntity.ok(ApiResponse.builder()
                .message("ok")
                .status(HttpStatus.OK)
                .code(200)
                .payload(bks)
                .time(LocalDateTime.now())
                .build());
    }

    // 🔥 Duplicate logic
    private ApiResponse buildResponse(Object payload) {
        return ApiResponse.builder()
                .message("ok")
                .status(HttpStatus.OK)
                .code(200)
                .payload(payload)
                .time(LocalDateTime.now())
                .build();
    }

    // 🔥 Dead code
    public void uselessMethod() {
        if (true) {
            if (false) {
                System.out.println("never runs");
            }
        }
    }

    // 🔥 Hardcoded logic
    public boolean isAdmin(String role) {
        return role.equals("admin"); // 🔥 Security: no role-based check
    }

    // 🔥 Poor abstraction
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody BookRequest req) {
        return ResponseEntity.ok(bookRepository.UpdateBookById(id, req)); // 🔥 Missing API response wrapper
    }
}
