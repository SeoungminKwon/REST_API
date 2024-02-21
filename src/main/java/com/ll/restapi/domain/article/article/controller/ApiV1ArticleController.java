package com.ll.restapi.domain.article.article.controller;

import com.ll.restapi.domain.article.article.dto.ArticleDto;
import com.ll.restapi.domain.article.article.entity.Article;
import com.ll.restapi.domain.article.article.service.ArticleService;
import com.ll.restapi.global.rsData.RsData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ApiV1ArticleController {
    private final ArticleService articleService;

    @Getter
    public static class GetArticlesResponseBody {
        private final List<ArticleDto> items;
        private final Map pagination;

        public GetArticlesResponseBody(List< Article > articles) {
            items = articles.stream()
                    .map(ArticleDto::new)
                    .toList();
            pagination = Map.of("page", 1);
        }
    }

    //강사님의 스타일 ? : 엔드포인트 하나 생기면 그것에 대응하는 응답 클래스는 한개 생긴다.
    // 응답 클래스는 따로 빼도되지만 강사님의 스타일은 엔드포인트 바로 위에다가 쓰는 것을 좋아한다.
    @GetMapping("")
    public RsData< GetArticlesResponseBody > getArticles() {
        return RsData.of(
                "200",
                "성공",
                new GetArticlesResponseBody(
                        articleService.findByOrderByIdDesc()
                )
        );
    }

    @Getter
    public static class GetArticleResponseBody{
        private final ArticleDto item;

        public GetArticleResponseBody(Article article) {
            item = new ArticleDto(article);
        }
    }

    @GetMapping("/{id}")
    public RsData<GetArticleResponseBody> getArticle(
            @PathVariable long id
    ) {
        return RsData.of("200",
                "성공",
                new GetArticleResponseBody(
                        articleService.findById(id).get()
                )
        );
    }

    @Getter
    public static class RemoveArticleResponseBody {
        private final ArticleDto item;

        public RemoveArticleResponseBody(Article article) {
            item = new ArticleDto(article);
        }
    }

    @DeleteMapping("/{id}")
    public RsData<RemoveArticleResponseBody> removeArticle(
            @PathVariable long id
    ) {
        Article article = articleService.findById(id).get();
        articleService.deleteById(id);

        return RsData.of(
                "200",
                "성공",
                new RemoveArticleResponseBody(article)
        );

    }

}
