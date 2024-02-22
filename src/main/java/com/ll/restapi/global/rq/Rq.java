package com.ll.restapi.global.rq;

import com.ll.restapi.domain.member.member.entity.Member;
import com.ll.restapi.domain.member.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final MemberService memberService;
    private Member member;

    public Member getMember() {
        if (member == null) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long memberId = Long.parseLong(user.getUsername());//Jwt필터에서 User리턴시 해당 유저의 username
            // param에 (String) userid를 적어줄 것임
            member = memberService.findById(memberId).get();

        }
        return member;
    }

    public String getHeader(String name) {
        return request.getHeader(name);
    }

}
