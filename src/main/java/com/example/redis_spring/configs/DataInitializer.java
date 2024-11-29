package com.example.redis_spring.configs;

import com.example.redis_spring.models.Book;
import com.example.redis_spring.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        if (bookRepository.count() == 0) {
            bookRepository.save(new Book(null, "1984", "George Orwell", 500L, "Dystopian", "Classic dystopian novel"));
            bookRepository.save(new Book(null, "To Kill a Mockingbird", "Harper Lee", 300L, "Classic", "Story about racism and justice"));
            bookRepository.save(new Book(null, "The Great Gatsby", "F. Scott Fitzgerald", 400L, "Classic", "Jazz Age novel"));
        }
    }
}
