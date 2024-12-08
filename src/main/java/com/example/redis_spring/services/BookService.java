package com.example.redis_spring.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.example.redis_spring.models.Book;
import com.example.redis_spring.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CacheService cacheService;



    public Book getBook(Long id) {
        final String cacheKey = "book:" + id;
        log.info("Deleting cache for popular books before retrieving book...");
        cacheService.deleteCachedObject("popular_books");

        // Проверяем кэш книги
        Book cachedBook = cacheService.getCachedObject(cacheKey, new TypeReference<>() {});
        if (cachedBook != null) {
            log.info("Book found in cache: {}", cachedBook);
            cachedBook.setPopularity(cachedBook.getPopularity() + 1);
            cacheService.cacheObject(cacheKey, cachedBook, 1, TimeUnit.MINUTES);
            bookRepository.save(cachedBook);
            return cachedBook;
        }

        log.info("Book not found in cache. Querying database...");
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setPopularity(book.getPopularity() + 1);
            bookRepository.save(book);
            cacheService.cacheObject(cacheKey, book, 1, TimeUnit.MINUTES);
            return book;
        }

        log.warn("Book not found for ID: {}", id);
        return null;
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

    public List<Book> getPopularBooks() {
        final String cacheKey = "popular_books";

        // Пробуем получить популярные книги из кэша
        List<Book> cachedBooks = cacheService.getCachedObject(cacheKey, new TypeReference<>() {});
        if (cachedBooks != null) {
            return cachedBooks;
        }

        // Если в кэше нет, получаем из базы данных
        List<Book> popularBooks = bookRepository.findPopularBooks();

        // Кэшируем результат
        cacheService.cacheObject(cacheKey, popularBooks, 5, TimeUnit.MINUTES);

        return popularBooks;
    }


}