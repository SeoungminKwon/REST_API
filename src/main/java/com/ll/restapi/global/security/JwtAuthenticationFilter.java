package com.ll.restapi.global.security;

import com.ll.restapi.domain.member.member.entity.Member;
import com.ll.restapi.domain.member.member.service.MemberService;
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
    private final MemberService memberService;
    @Override
    @SneakyThrows //해당 메서드에서 체크된 예외를 선언하지 않고, 던지게 해줌
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String apiKey = request.getHeader("X-ApiKey"); //사용자가 정의한 헤더는 X-??? 이런식으로 작성하는 것이 관례임

        if (apiKey != null) {
            Member member = memberService.findByApiKey(apiKey).get();

            User user = new User(
                    member.getUsername(),
                    member.getPassword(),
                    List.of()
            );

            Authentication auth = new UsernamePasswordAuthenticationToken( //Authentication 객체 생성
                    user,
                    user.getPassword(),
                    user.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

        }
        filterChain.doFilter(request, response);
    }
}
