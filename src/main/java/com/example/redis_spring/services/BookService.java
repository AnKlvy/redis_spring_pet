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
        log.info("Attempting to delete cache for popular books before retrieving book...");
        cacheService.deleteCachedObject("popular_books");

        log.info("Checking cache for book with ID: {}", id);
        Book cachedBook = cacheService.getCachedObject(cacheKey, new TypeReference<>() {});
        if (cachedBook != null) {
            log.info("Book found in cache: {}", cachedBook);
            cachedBook.setPopularity(cachedBook.getPopularity() + 1);
            cacheService.cacheObject(cacheKey, cachedBook, 1, TimeUnit.MINUTES);
            bookRepository.save(cachedBook);
            return cachedBook;
        }

        log.info("Book not found in cache. Querying database for book with ID: {}", id);
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            log.info("Book found in database: {}", book);
            book.setPopularity(book.getPopularity() + 1);
            bookRepository.save(book);
            cacheService.cacheObject(cacheKey, book, 1, TimeUnit.MINUTES);
            return book;
        }

        log.warn("Book not found for ID: {}", id);
        return null;
    }

    public void saveBook(Book book) {
        log.info("Updating book in database: {}", book);
        bookRepository.save(book);

        log.info("Caching updated book with ID: {}", book.getId());
        cacheService.cacheObject("book:" + book.getId(), book, 1, TimeUnit.MINUTES);
    }
    public Book updateBook(Long id, Book updatedBook) {
        final String cacheKey = "book:" + id;
        log.info("Attempting to update book with ID: {}", id);

        Optional<Book> existingBookOptional = bookRepository.findById(id);
        if (existingBookOptional.isPresent()) {
            Book existingBook = existingBookOptional.get();

            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setGenre(updatedBook.getGenre());
            existingBook.setDescription(updatedBook.getDescription());
            existingBook.setPopularity(updatedBook.getPopularity());

            bookRepository.save(existingBook);
            log.info("Book updated in database: {}", existingBook);

            // Обновляем кеш
            cacheService.cacheObject(cacheKey, existingBook, 1, TimeUnit.MINUTES);
            log.info("Updated book cached with key: {}", cacheKey);

            return existingBook;
        }

        log.warn("Book with ID: {} not found for update", id);
        return null;
    }

    public void deleteBook(Long bookId) {
        log.info("Deleting book with ID: {}", bookId);
        bookRepository.deleteById(bookId);

        log.info("Deleting book from cache with key: {}", "book:" + bookId);
        cacheService.deleteCachedObject("book:" + bookId);
    }

    public List<Book> getPopularBooks() {
        final String cacheKey = "popular_books";

        log.info("Checking cache for popular books...");
        List<Book> cachedBooks = cacheService.getCachedObject(cacheKey, new TypeReference<>() {});
        if (cachedBooks != null) {
            log.info("Popular books found in cache.");
            return cachedBooks;
        }

        log.info("Popular books not found in cache. Querying database for popular books...");
        List<Book> popularBooks = bookRepository.findPopularBooks();

        log.info("Caching popular books with key: {}", cacheKey);
        cacheService.cacheObject(cacheKey, popularBooks, 5, TimeUnit.MINUTES);

        return popularBooks;
    }
}
