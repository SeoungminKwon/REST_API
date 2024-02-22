package com.ll.restapi.global.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String SECRET_KEY = "abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890";

    public static String encode(Map< String, String > data) {
        //jwt 내용 ?
        //Claims 객체는 JWT 페이로드에 들어갈 여러 claims를 설정 할 수 있다.
        Claims claims = Jwts
                .claims()
                .setSubject("RESTAPI jwt") //토큰 이름
                .add("type", data)
                .build();

        Date now = new Date();
        Date validity = new Date(now.getTime() + 1000 * 60 * 5); // 5분

        return Jwts.builder()
                .setClaims(claims) //만들어둔 Claims 객체를 설정
                .setIssuedAt(now) // JWT 발행시간을 현제 시간으로 설정
                .setExpiration(validity) //만료 시간 설정
                //JWT는 키가 있으면 , JWT토큰에 해커가 장난질 쳤는지 안쳤는지 알 수 있음
                //홰당 키는 JWT온라인에 등록을 해줘야함
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY)
                .compact();
    }

    public static Claims decode(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }
}
