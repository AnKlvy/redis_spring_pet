//package com.example.redis_spring.services;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Устанавливаем фиксированное имя и пароль
//        return User.builder()
//                .username("AnKv")  // Фиксированное имя
//                .password("{noop}AnKv")  // Фиксированный пароль (noop — без кодирования)
//                .roles("admin")  // Роль пользователя
//                .build();
//    }
//}
