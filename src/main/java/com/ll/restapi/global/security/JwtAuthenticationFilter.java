package com.ll.restapi.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    @SneakyThrows //해당 메서드에서 체크된 예외를 선언하지 않고, 던지게 해줌
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        User user = new User("user1", "", List.of()); //가짜 유저 생성

        Authentication auth = new UsernamePasswordAuthenticationToken( //Authentication 객체 생성
                user,
                user.getPassword(),
                user.getAuthorities()
        );

        //편법으로 스프링 시큐리티가 현제 로그인했다고 생각하게 만듬 - 스프링 시큐리티는 context에 Authentication이 있으면, 로그인 되어있다고 판단
        //Spring security에서 Authentication객체를 얻을때,  SecurityContextHolder.getContext().getAuthentication를 통해서 얻음
        SecurityContextHolder.getContext().setAuthentication(auth); //현제 리퀘스트에서만 유지됨
        /** 세션에 객체가 저장되어있으면, 스프링 시큐리티가 세션에서 값을 가져와서 값을 넣어줌
            (위의 코드가 자동이라고 생각하면됨)
            현제는 세션을 사용 못하니깐, 수동으로 스프링 시큐리티가 해줬던 과정을 해줌
         */



        filterChain.doFilter(request, response);
    }
}
