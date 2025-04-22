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
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Data
@RequestMapping("api/v1/books")
public class BookController {
    private final BookRepository bookRepository;
    @PostMapping
    @Operation(summary = "Create book")
    public ResponseEntity<?> createBook (@RequestBody BookRequest bookrequest){
        Book book  = bookRepository.createBook(bookrequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("created book successfully")
                .payload(book)
                .status(HttpStatus.CREATED)
                .code(201)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);

    }
    @GetMapping("{id}")
    @Operation(summary = "Get book by id ")
    public ResponseEntity<?> getBookById (@PathVariable UUID id){

        Book book  = bookRepository.getBookById(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Get book successfully")
                .status(HttpStatus.OK)
                .code(200)
                .payload(book)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);

    }
    @GetMapping
    @Operation(summary = "Get all book ")
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks(){
        List<Book> books = bookRepository.getAllBooks();
        ApiResponse<List<Book>> apiResponse = ApiResponse.<List<Book>>builder()
                .message("Get all book successfully")
                .status(HttpStatus.OK)
                .code(200)
                .payload(books)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "delete book by id")

    public ResponseEntity<?> deleteBookById (@PathVariable UUID id){
        bookRepository.deleteBookById(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Delete successfully")
                .code(200)
                .status(HttpStatus.OK)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/{id}")
    @Operation(summary = "update Book by id")
    public ResponseEntity <?> updateBookById (@PathVariable UUID id , @RequestBody BookRequest bookRequest){

        Book book = bookRepository.UpdateBookById(id,bookRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Update book successfully")
                .code(200)
                .status(HttpStatus.OK)
                .payload(book)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);

    }
    @GetMapping("/title")
    @Operation(summary = "Get books by title")
    public ResponseEntity<?> getBooksByTitle(@RequestParam String title) {
        List<Book> books = bookRepository.getBooksByTitle(title);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Get book by title successfully")
                .status(HttpStatus.OK)
                .code(200)
                .payload(books)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }


}

