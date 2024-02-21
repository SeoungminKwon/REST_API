package com.ll.restapi.domain.article.article.controller;

import com.ll.restapi.domain.article.article.entity.Article;
import com.ll.restapi.domain.article.article.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest //스프링 부트 테스트에 필요한 의존성 주입과 애플리케이션 컨텍스트 로딩을 위한 어노테이션
@AutoConfigureMockMvc //MockMvc 인스턴스를 자동으로 구성하고, 주입하기 위한 어노테이션
@ActiveProfiles("test") //Test 프로필 활성화
@Transactional
class ApiV1ArticleControllerTest {
    @Autowired //MockMvc인스턴스 자동 주입
    private MockMvc mvc;

    @Autowired
    private ArticleService articleService;

    //날짜 패턴 정규식
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.?\\d{0,7}";

    @Test
    @DisplayName("GET /api/v1/articles")
    void t1() throws Exception {
        //WHEN
        ResultActions resultActions = mvc
                .perform(get("/api/v1/articles")) //엔드포인트 get요청을 시뮬레이션
                .andDo(print());//요청과 응답을 콘솔에 출력

        //WEHN
        resultActions
                .andExpect(status().isOk()) //HTTP 상태코드 검증
                .andExpect(handler().handlerType(ApiV1ArticleController.class)) //요청 처리 클래스 ApiV1ArticleController인지 검증
                .andExpect(handler().methodName("getArticles"))
                .andExpect(jsonPath("$.data.items[0].id", instanceOf(Number.class))) // 값이 숫자인지 체크
                .andExpect(jsonPath("$.data.items[0].createDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.items[0].modifyDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.items[0].authorId", instanceOf(Number.class))) // 값이 숫자인지 체크
                .andExpect(jsonPath("$.data.items[0].authorName", notNullValue())) //null이 아닌 값
                .andExpect(jsonPath("$.data.items[0].title", notNullValue()))//null이 아닌 값
                .andExpect(jsonPath("$.data.items[0].body", notNullValue()));//null이 아닌 값
    }

    @Test
    @DisplayName("GET /api/v1/articles/1")
    void t2() throws Exception {
        //WHEN
        ResultActions resultActions = mvc
                .perform(get("/api/v1/articles/1")) //엔드포인트 get요청을 시뮬레이션
                .andDo(print());//요청과 응답을 콘솔에 출력

        //WEHN
        resultActions
                .andExpect(status().isOk()) //HTTP 상태코드 검증
                .andExpect(handler().handlerType(ApiV1ArticleController.class)) //요청 처리 클래스 ApiV1ArticleController인지 검증
                .andExpect(handler().methodName("getArticle"))
                .andExpect(jsonPath("$.data.item.id", instanceOf(Number.class))) // 값이 숫자인지 체크
                .andExpect(jsonPath("$.data.item.createDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.item.modifyDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.item.authorId", instanceOf(Number.class))) // 값이 숫자인지 체크
                .andExpect(jsonPath("$.data.item.authorName", notNullValue())) //null이 아닌 값
                .andExpect(jsonPath("$.data.item.title", notNullValue()))//null이 아닌 값
                .andExpect(jsonPath("$.data.item.body", notNullValue()));//null이 아닌 값
    }

    @Test
    @DisplayName("DELETE /api/v1/articles/1")
    void t3() throws Exception {
        //WHEN
        ResultActions resultActions = mvc
                .perform(delete("/api/v1/articles/1")) //엔드포인트 delete 요청을 시뮬레이션
                .andDo(print());//요청과 응답을 콘솔에 출력

        //WEHN
        resultActions
                .andExpect(status().isOk()) //HTTP 상태코드 검증
                .andExpect(handler().handlerType(ApiV1ArticleController.class)) //요청 처리 클래스 ApiV1ArticleController인지 검증
                .andExpect(handler().methodName("removeArticle"))
                .andExpect(jsonPath("$.data.item.id", instanceOf(Number.class))) // 값이 숫자인지 체크
                .andExpect(jsonPath("$.data.item.createDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.item.modifyDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.item.authorId", instanceOf(Number.class))) // 값이 숫자인지 체크
                .andExpect(jsonPath("$.data.item.authorName", notNullValue())) //null이 아닌 값
                .andExpect(jsonPath("$.data.item.title", notNullValue()))//null이 아닌 값
                .andExpect(jsonPath("$.data.item.body", notNullValue()));//null이 아mArticle article1 = articleService.findById(1L).orElse(null);
        Article article1 = articleService.findById(1L).orElse(null);
        assertThat(article1).isNull();
    }

    @Test
    @DisplayName("PUT /api/v1/articles/1")
    void t4() throws Exception {
        //WHEN
        ResultActions resultActions = mvc
                .perform(
                        put("/api/v1/articles/1") //엔드포인트에 PUT요청 시뮬레이션
                                .contentType(MediaType.APPLICATION_JSON) //데이터 타입 지정
                                //body내용 확인
                                .content("""
                                           {
                                            "title": "제목1-수정",
                                            "body": "내용1-수정"
                                            }
                                        """)
                )
                .andDo(print());

        //THEN
        resultActions
                .andExpect(status().isOk()) //HTTP 상태코드 검증
                .andExpect(handler().handlerType(ApiV1ArticleController.class)) //요청 처리 클래스 검증
                .andExpect(handler().methodName("modifyArticle")) //요청 처리 메서드 검증
                .andExpect(jsonPath("$.data.item.id", instanceOf(Number.class))) // 값이 숫자인지 체크
                .andExpect(jsonPath("$.data.item.createDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.item.modifyDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.item.authorId", instanceOf(Number.class))) // 값이 숫자인지 체크
                .andExpect(jsonPath("$.data.item.authorName", notNullValue())) //null이 아닌 값
                .andExpect(jsonPath("$.data.item.title", is("제목1-수정")))
                .andExpect(jsonPath("$.data.item.body", is("내용1-수정")));
    }

    @Test
    @DisplayName("POST /api/v1/articles")
    void t5() throws Exception {
        //WHEN
        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/articles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "title" : "제목 new",
                                            "body" : "내용 new"
                                        }
                                        """)
                )
                .andDo(print());

        //THEN
        resultActions
                .andExpect(status().isOk()) //HTTP 상태코드 검증
                .andExpect(handler().handlerType(ApiV1ArticleController.class)) //요청 처리 클래스 검증
                .andExpect(handler().methodName("writeArticle")) //요청 처리 메서드 검증
                .andExpect(jsonPath("$.data.item.id", instanceOf(Number.class))) // 값이 숫자인지 체크
                .andExpect(jsonPath("$.data.item.createDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.item.modifyDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.item.authorId", instanceOf(Number.class))) // 값이 숫자인지 체크
                .andExpect(jsonPath("$.data.item.authorName", notNullValue())) //null이 아닌 값
                .andExpect(jsonPath("$.data.item.title", is("제목 new")))
                .andExpect(jsonPath("$.data.item.body", is("내용 new")));
    }

}