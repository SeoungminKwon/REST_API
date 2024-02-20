package com.ll.restapi.domain.article.article.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.restapi.domain.member.member.entity.Member;
import com.ll.restapi.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@Entity
@SuperBuilder //클래스 상속 구조에서 사용하면 부모클래스의 필드도 빌더 패턴에 포함 가능
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true) //부모클래스의 toString도 메서드 결과에 포함시킴
public class Article extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;
    private String title;
    private String body;

}
