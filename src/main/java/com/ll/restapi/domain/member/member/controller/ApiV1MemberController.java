package com.ll.restapi.domain.member.member.controller;

import com.ll.restapi.domain.member.member.dto.MemberDto;
import com.ll.restapi.domain.member.member.entity.Member;
import com.ll.restapi.domain.member.member.service.MemberService;
import com.ll.restapi.global.rq.Rq;
import com.ll.restapi.global.rsData.RsData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
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

        public LoginResponseBody(Member member) {
            this.item = new MemberDto(member);
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

        return RsData.of(
                "200",
                "로그인 성공",
                new LoginResponseBody(
                        member
                )
        );
    }

    //전 기기 로그아웃
    @PreAuthorize("isAuthenticated()") // JwtAuthenticationFilter에서 시큐리티에 auth 객체를 넣어줘서 작동함
    @PostMapping("/apiKey")
    //타입의 유연성을 확보하기 위해서 ?(와일드 카드)를 작성함
    public RsData< ? > regenApiKey() {
        Member member = rq.getMember();

        memberService.regenApiKey(member);
        return RsData.of(
                "200",
                "해당 키가 재생성 되었습니다."
        );
    }
}
