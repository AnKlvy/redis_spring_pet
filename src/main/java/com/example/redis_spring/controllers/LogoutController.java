//package com.example.redis_spring.controllers;
//
//import com.example.redis_spring.services.CacheService;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.session.Session;
//import org.springframework.session.SessionRepository;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//@Slf4j
//@Controller
//@RequiredArgsConstructor
//public class LogoutController {
//    private final CacheService cacheService;
//
//    @Autowired
//    private SessionRepository<? extends Session> sessionRepository;
//
//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request) {
//        String sessionId = request.getSession().getId();
//        cacheService.deleteCachedObject(sessionId);  // Удаляем сессию из Redis
//        return "redirect:/login?logout";
//    }
//}
