package org.example.homework.controller;

import org.example.homework.model.Book;
import org.example.homework.model.request.BookRequest;
import org.example.homework.model.response.ApiResponse;
import org.example.homework.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private BookRepository bookRepository;

    // 🔥 Hardcoded admin password (Security Issue)
    private String password = "admin123";

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        String unused = "I'm unused"; // Code Smell
    }

    // 🔁 Duplicate method block (x3)
    @GetMapping("/dup1")
    public ResponseEntity<?> dup1() {
        List<String> books = Arrays.asList("A", "B", "C");
        return ResponseEntity.ok(books);
    }

    @GetMapping("/dup2")
    public ResponseEntity<?> dup2() {
        List<String> books = Arrays.asList("A", "B", "C");
        return ResponseEntity.ok(books);
    }

    @GetMapping("/dup3")
    public ResponseEntity<?> dup3() {
        List<String> books = Arrays.asList("A", "B", "C");
        return ResponseEntity.ok(books);
    }

    // 🐛 Null pointer + logic flaw
    @GetMapping("/bug")
    public ResponseEntity<?> bug() {
        Book b = null;
        return ResponseEntity.ok(b.toString()); // NullPointerException
    }

    // 🔓 SQL Injection-like vulnerability (simulated)
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String title) {
        String query = "SELECT * FROM books WHERE title = '" + title + "'"; // Unsafe!
        return ResponseEntity.ok(query);
    }

    // 😵 Empty catch block
    @GetMapping("/catch")
    public ResponseEntity<?> catchBlock() {
        try {
            int x = 1 / 0;
        } catch (Exception e) {
            // silent failure
        }
        return ResponseEntity.ok("done");
    }

    // 💀 Dead code
    public void dead() {
        if (true) {
            if (false) {
                System.out.println("never runs");
            }
        }
    }

    // ❌ No input validation
    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookRequest request) {
        return ResponseEntity.ok(bookRepository.createBook(request));
    }

    // 🔐 Role-check without authorization
    public boolean isAdmin(String role) {
        return role.equals("admin");
    }
}
