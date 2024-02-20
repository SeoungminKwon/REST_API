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
        Member member2 = memberservice.join("user1", "1234", "user1@test.com", "user1").getData();
        Member member3 = memberservice.join("user2", "1234", "user2@test.com", "user2").getData();

        Article article1 = articleService.write(member1, "제목1", "내용1").getData();
        Article article2 = articleService.write(member2, "제목2", "내용2").getData();
        Article article3 = articleService.write(member3, "제목3", "내용3").getData();
        Article article4 = articleService.write(member1, "제목4", "내용4").getData();
        Article article5 = articleService.write(member2, "제목5", "내용5").getData();
        Article article6 = articleService.write(member3, "제목6", "내용6").getData();
        Article article7 = articleService.write(member1, "제목7", "내용7").getData();
        Article article8 = articleService.write(member2, "제목8", "내용8").getData();
        Article article9 = articleService.write(member3, "제목9", "내용9").getData();
        Article article10 = articleService.write(member1, "제목10", "내용10").getData();
    }

}
