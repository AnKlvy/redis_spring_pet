package com.example.redis_spring.rabbitmq;

import com.example.redis_spring.models.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookPublisher {

    private final RabbitTemplate rabbitTemplate;

        @Value("${spring.rabbitmq.template.exchange}")
    private String topicExchange;

    public void sendBookByGenre(Book book) {
        String routingKey = "book." + book.getGenre();
        rabbitTemplate.convertAndSend(topicExchange, routingKey, book);
    }

}