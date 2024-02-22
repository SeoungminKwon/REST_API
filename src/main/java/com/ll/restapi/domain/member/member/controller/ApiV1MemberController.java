package com.ll.restapi.domain.member.member.controller;

import com.ll.restapi.domain.member.member.dto.MemberDto;
import com.ll.restapi.domain.member.member.entity.Member;
import com.ll.restapi.domain.member.member.service.MemberService;
import com.ll.restapi.global.rq.Rq;
import com.ll.restapi.global.rsData.RsData;
import com.ll.restapi.global.util.jwt.JwtUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @Getter
    @Setter
    public static class LoginRequestBody {
        private String username;
        private String password;
    }

    @Getter
    public static class LoginResponseBody {
        private final MemberDto item;
        private final String accessToken;

        public LoginResponseBody(Member member, String accessToken) {
            this.item = new MemberDto(member);
            this.accessToken = accessToken;
        }
    }

    @PostMapping("/login")
    public RsData< LoginResponseBody > login(
            @RequestBody LoginRequestBody requestBody
    ) {
        RsData< Member > checkRs = memberService.checkUsernameAndPassword(
                requestBody.getUsername(),
                requestBody.getPassword()
        );

        Member member = checkRs.getData();

        Long id = member.getId();
        String accessToken = JwtUtil.encode(Map.of("id", id.toString()));


        return RsData.of(
                "200",
                "로그인 성공",
                new LoginResponseBody(member, accessToken)
        );
    }
}
