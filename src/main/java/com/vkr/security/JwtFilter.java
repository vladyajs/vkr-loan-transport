package com.vkr.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends GenericFilterBean {
    private final String SECRET_KEY = "vkr"; // ДОЛЖЕН БЫТЬ В .env ИЛИ СЕКРЕТАХ!

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String authHeader = httpRequest.getHeader("Authorization");

        // Проверяем, есть ли заголовок "Authorization" и начинается ли он с "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Извлекаем сам токен

            try {
                // Используем parserBuilder() вместо parser()
                JwtParser jwtParser = Jwts.parser()
                        .setSigningKey(SECRET_KEY)  // Устанавливаем секретный ключ для подписи
                        .build(); // Строим экземпляр JwtParser

                // Парсим токен
                Claims claims = jwtParser.parseClaimsJws(token).getBody();

                String username = claims.getSubject(); // Получаем имя пользователя из токена
                String role = claims.get("role", String.class); // Получаем роль пользователя (если она есть в токене)

                // Создаем объект аутентификации и устанавливаем его в контекст безопасности
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList()); // Поскольку пароль не передается, указываем null
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((jakarta.servlet.http.HttpServletRequest) httpRequest));

                // Устанавливаем аутентификацию в SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException | IllegalArgumentException e) {
                // Если токен некорректный, очищаем контекст безопасности
                SecurityContextHolder.clearContext();
            }
        }

        // Продолжаем выполнение цепочки фильтров
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
