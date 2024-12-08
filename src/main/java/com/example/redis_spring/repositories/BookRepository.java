package com.example.redis_spring.repositories;

import com.example.redis_spring.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT * FROM book ORDER BY popularity DESC LIMIT 5", nativeQuery = true)
    List<Book> findPopularBooks();

}