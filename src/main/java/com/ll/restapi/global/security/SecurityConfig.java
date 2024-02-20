package com.ll.restapi.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //먼저쓴 requestMatchers는 우선 통과됨
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                //처음 단계에서 통과하면 나머지 단계는 통과
                                .requestMatchers(
                                        //정적 리소스 접근 허용
                                        PathRequest.toStaticResources().atCommonLocations(), //기본적으로 사용하는 CSS, jS, IMG, FAVICON ... 등을 뚫어줌
                                        //경로 패턴에 대한 접근 허
                                        new AntPathRequestMatcher("/resources/**"),
                                        new AntPathRequestMatcher("/h2-console/**")
                                )
                                .permitAll()
                                .requestMatchers(
                                        "/adm/**"
                                )
                                .hasRole("ADMIN")
                                .anyRequest()
                                .permitAll()
                )
                //headers, csrf -> h2콘솔 쓰기 위한것들
                .headers(
                        headers -> headers
                                .addHeaderWriter(
                                        new XFrameOptionsHeaderWriter(
                                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)
                                )
                )
                .csrf(
                        csrf -> csrf
                                .ignoringRequestMatchers(
                                        "/h2-console/**"
                                )
                )
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/member/login")
                )
                .logout(
                        logout -> logout
                                //.logoutUrl(???) 이렇게 적는 것과의 차이점 -> 오직 POST로만 접근 가능
                                //logoutRequestMatcher로 하면 get방식도 접근이 허용됨
                                .logoutRequestMatcher(
                                        new AntPathRequestMatcher("/member/logout")
                                )
                );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
