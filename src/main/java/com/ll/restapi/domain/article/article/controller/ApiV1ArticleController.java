package com.ll.restapi.domain.article.article.controller;

import com.ll.restapi.domain.article.article.dto.ArticleDto;
import com.ll.restapi.domain.article.article.entity.Article;
import com.ll.restapi.domain.article.article.service.ArticleService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ApiV1ArticleController {
    private final ArticleService articleService;

    @Getter
    public static class GetArticleResponseBody{
        private final List<ArticleDto> items;
        private final Map pagination;

        public GetArticleResponseBody(List< Article > articles) {
            items = articles.stream()
                    .map(ArticleDto::new)
                    .toList();
            pagination = Map.of("page", 1);
        }
    }

    @GetMapping("")
    public GetArticleResponseBody getArticles() {
        return new GetArticleResponseBody(articleService.findByOrderByIdDesc());
    }
}
