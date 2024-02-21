package com.ll.restapi.domain.article.article.service;

import com.ll.restapi.domain.article.article.entity.Article;
import com.ll.restapi.domain.article.article.repository.ArticleRepository;
import com.ll.restapi.domain.member.member.entity.Member;
import com.ll.restapi.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public RsData< Article > write(Member author, String title, String body){
        Article article = Article.builder()
                .modifyDate(LocalDateTime.now())
                .author(author)
                .title(title)
                .body(body)
                .build();
        articleRepository.save(article);

        return RsData.of("200", "%d번 게시글이 작성되었습니다.".formatted(article.getId()), article);
    }

    public Optional< Article > findById(Long id) {
        return articleRepository.findById(id);
    }

    @Transactional
    public void modify(Article article, String title, String body) {
        article.setTitle(title);
        article.setBody(body);
    }

    public List< Article > findAll() {
        return articleRepository.findAll();
    }

    public List< Article > findByOrderByIdDesc() {
        return articleRepository.findByOrderByIdDesc();
    }

    @Transactional
    public void deleteById(long id) {
        articleRepository.deleteById(id);
    }
}
