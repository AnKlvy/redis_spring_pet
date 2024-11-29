package com.example.redis_spring.controllers;

import com.example.redis_spring.models.Book;
import com.example.redis_spring.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

//    @GetMapping
//    public ResponseEntity<List<Book>> getBooks() {
//        List<Book> books = bookService.getBooks();
//        return books != null ? ResponseEntity.ok(books) : ResponseEntity.notFound().build();
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Book> addOrUpdateBook(@RequestBody Book book) {
        bookService.updateBook(book);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}
