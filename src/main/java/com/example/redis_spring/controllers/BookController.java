package com.example.redis_spring.controllers;

import com.example.redis_spring.models.Book;
import com.example.redis_spring.services.BookService;
import com.example.redis_spring.services.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final MinioService minioService;

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        bookService.saveBook(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/popular")
    public ResponseEntity<List<Book>> getPopularBooks() {
        List<Book> popularBooks = bookService.getPopularBooks();
        return ResponseEntity.ok(popularBooks);
    }
    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String response = minioService.uploadFile(file.getBytes(), file.getOriginalFilename());
            return ResponseEntity.ok("Файл успешно загружен: " + response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка загрузки файла: " + e.getMessage());
        }
    }

    @GetMapping("/images")
    public ResponseEntity<String> getUploadedImages() {
        String response = minioService.getFiles();
        return ResponseEntity.ok(response);
    }
}


