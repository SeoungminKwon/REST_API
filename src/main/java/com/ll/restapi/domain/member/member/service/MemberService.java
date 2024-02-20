package com.ll.restapi.domain.member.member.service;

import com.ll.restapi.domain.member.member.entity.Member;
import com.ll.restapi.domain.member.member.repository.MemberRepository;
import com.ll.restapi.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RsData< Member > join(String username, String password, String email, String nickname){
        Member member = Member.builder()
                .modifyDate(LocalDateTime.now())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();

        memberRepository.save(member);
        return RsData.of("200", "%d님 가입을 환영합니다.".formatted(username), member);
    }

    public Optional< Member > findById(long id) {
        return memberRepository.findById(id);
    }

    public long count() {
        return memberRepository.count();
    }
}
