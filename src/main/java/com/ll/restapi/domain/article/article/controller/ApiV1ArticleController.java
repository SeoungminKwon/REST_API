package com.ll.restapi.domain.article.article.controller;

import com.ll.restapi.domain.article.article.entity.Article;
import com.ll.restapi.domain.article.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ApiV1ArticleController {
    private final ArticleService articleService;

    @GetMapping("")
    public List< Article > getArticles() {
        return articleService.findAll();
    }
}
