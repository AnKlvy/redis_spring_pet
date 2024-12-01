package com.example.redis_spring;

import jakarta.servlet.http.HttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class RedisSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisSpringApplication.class, args);
	}
	@GetMapping("/")
	public String hello() {
		return "hello";
	}

	@GetMapping("/session")
	public String getSession(HttpSession session) {
		return "session: " + session;
	}
}
