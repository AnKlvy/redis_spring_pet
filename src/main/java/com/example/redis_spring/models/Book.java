package com.example.redis_spring.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Используем автоинкремент
    private Long id;
    private String title;
    private String author;
    private Long price;
    private String genre;
    private String description;
}