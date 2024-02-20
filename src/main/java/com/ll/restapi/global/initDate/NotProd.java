package com.ll.restapi.global.initDate;

import com.ll.restapi.domain.article.article.entity.Article;
import com.ll.restapi.domain.article.article.service.ArticleService;
import com.ll.restapi.domain.member.member.entity.Member;
import com.ll.restapi.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class NotProd {
    @Autowired
    @Lazy
    private NotProd self; //self는 final로 불가 @Autowired로만 가능

    private final MemberService memberservice;
    private final ArticleService articleService;


    @Bean
    public ApplicationRunner initNotProd(){
        return args -> {
            self.work1();// 내부 호출은 transaction이 발현이 안됨 그래서 self.~~ 이렇게 한 것
            //자기 자신의 프록시를 통해 메서드를 호출함으로서 트랜젝션이 정상적으로 작동하도록
        };
    }

    @Transactional
    public void work1() {

        if(memberservice.count() > 0)return;

        Member member1 = memberservice.join("admin", "1234", "admin@test.com", "관리자").getData();
        Article article1 = articleService.write(member1, "제목1", "내용1").getData();
    }

}
