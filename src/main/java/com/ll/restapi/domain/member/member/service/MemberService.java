package com.ll.restapi.domain.member.member.service;

import com.ll.restapi.domain.member.member.entity.Member;
import com.ll.restapi.domain.member.member.repository.MemberRepository;
import com.ll.restapi.global.rsData.RsData;
import com.ll.restapi.global.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
                .nickname(nickname)
                .build();

        memberRepository.save(member);
        return RsData.of("200", "%d님 가입을 환영합니다.".formatted(member.getId()), member);
    }

    public Optional< Member > findById(long id) {
        return memberRepository.findById(id);
    }

    public long count() {
        return memberRepository.count();
    }

    public Optional< Member > findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public User getUserFromAPiKey(String apiKey) {
        Claims claims = JwtUtil.decode(apiKey);

        Map< String, Object > data = (Map< String, Object >) claims.get("data");
        String id = (String) data.get("id");
        List<? extends GrantedAuthority > authorities = ((List< String >) data.get("authorities"))
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new User(
                id,
                "",
                authorities
        );
    }

    public RsData< Member> checkUsernameAndPassword(String username, String password) {
        Optional< Member > memberOp = findByUsername(username);

        if (memberOp.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        if (!passwordEncoder.matches(password, memberOp.get().getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return RsData.of("200", "로그인 성공", memberOp.get());

    }


}
