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
            bookRepository.save(new Book(null, "1984", "George Orwell", 500L, "Dystopian", "Classic dystopian novel", 0));
            bookRepository.save(new Book(null, "To Kill a Mockingbird", "Harper Lee", 300L, "Classic", "Story about racism and justice", 0));
            bookRepository.save(new Book(null, "The Great Gatsby", "F. Scott Fitzgerald", 400L, "Classic", "Jazz Age novel", 0));
            bookRepository.save(new Book(null, "Brave New World", "Aldous Huxley", 350L, "Dystopian", "A novel about a future society", 0));
            bookRepository.save(new Book(null, "The Catcher in the Rye", "J.D. Salinger", 250L, "Fiction", "Story of a troubled teenager", 0));
            bookRepository.save(new Book(null, "The Picture of Dorian Gray", "Oscar Wilde", 300L, "Gothic", "A novel about vanity and corruption", 0));
        }
    }
}
