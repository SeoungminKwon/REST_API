package com.ll.restapi.domain.article.article.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest //스프링 부트 테스트에 필요한 의존성 주입과 애플리케이션 컨텍스트 로딩을 위한 어노테이션
@AutoConfigureMockMvc //MockMvc 인스턴스를 자동으로 구성하고, 주입하기 위한 어노테이션
@ActiveProfiles("test") //Test 프로필 활성화
class ApiV1ArticleControllerTest {
    @Autowired //MockMvc인스턴스 자동 주입
    private MockMvc mvc;

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
                .andExpect(jsonPath("$.data.items[0].id", is(10))) //JSON 응답의 특정경로 값 검증
                .andExpect(jsonPath("$.data.items[0].title", is("제목10")));//JSON 응답의 특정경로 값 검증
    }
}