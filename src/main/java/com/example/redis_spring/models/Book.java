    package com.example.redis_spring.models;

    import jakarta.persistence.*;
    import lombok.*;

    import java.io.Serializable;
    import org.springframework.data.redis.core.RedisHash;

    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "book")
//    @RedisHash("book")
    public class Book implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String title;
        private String author;
        private Long price;
        private String genre;
        private String description;
        private Integer popularity;
    }