package com.example.redis_spring.services;

import com.example.redis_spring.models.Book;
import com.example.redis_spring.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CacheService cacheService;

//    public List<Book> getBooks() {
//        final String cacheKey = "books";
//
//        List<Book> cachedBooks = (List<Book>) cacheService.getCachedObject(cacheKey);
//
//        if (cachedBooks != null && !cachedBooks.isEmpty()) {
//            return cachedBooks;
//        }
//
//        List<Book> books = bookRepository.findAll();
//
//        if (!books.isEmpty()) {
//            cacheService.cacheObject(cacheKey, books, 1, TimeUnit.MINUTES);
//        }
//
//        return books;
//    }


    public Book getBook(Long id) {
        final String cacheKey = "book:" + id;

        Book cachedBook = (Book) cacheService.getCachedObject(cacheKey);

        if (cachedBook != null) {
            return cachedBook;
        }

        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(p -> cacheService.cacheObject(cacheKey, p, 1, TimeUnit.MINUTES));

        return book.orElse(null);
    }

    public void updateBook(Book book) {
        // Обновляем продукт в базе данных
        bookRepository.save(book);

        // Обновляем продукт в кэше
        cacheService.cacheObject("book:" + book.getId(), book, 1, TimeUnit.MINUTES);
    }

    public void deleteBook(Long bookId) {
        // Удаляем продукт из базы данных
        bookRepository.deleteById(bookId);

        // Удаляем продукт из кэша
        cacheService.deleteCachedObject("book:" + bookId);
    }
}