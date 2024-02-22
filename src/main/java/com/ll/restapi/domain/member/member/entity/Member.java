package com.ll.restapi.domain.member.member.entity;

import com.ll.restapi.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@SuperBuilder //부모클래스도 빌더패턴으로 가능
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true) //부모클래스의 toString도 같이 호출
public class Member extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private String nickname;


    public String getName(){
        return username;
    }

    public List< ? extends GrantedAuthority > getAuthorities() {
        return getAuthoritiesAsStrList()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public List<String> getAuthoritiesAsStrList() {
        return List.of("ROLE_MEMBER");
    }
}
