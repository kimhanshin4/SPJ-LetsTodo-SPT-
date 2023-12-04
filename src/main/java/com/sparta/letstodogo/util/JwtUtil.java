package com.sparta.letstodogo.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;
import jakarta.annotation.*;
import jakarta.servlet.http.*;
import java.lang.SecurityException;
import java.security.*;
import java.util.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization"; //Header에 넣어주기위한
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.key}")
    private String secretKey;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private Key key; //위 secretKey로 생성


    @PostConstruct //Key값 bean이 생성될 때 같이 만들어질 수 있도록
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey); //secretKey를 기준으로 decoding
        key = Keys.hmacShaKeyFor(bytes); //실제 사용할 Key값 생성
    }

    //Author에 사용
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7); //"Bearer "를 자른 응답값 return
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token);//생성된 Key값을 Token에 넣어 줌
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false; //유효 Token이 아닐 시 예외처리발생
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    //JWT Token 생성
    public String createToken(String username) {
        Date date = new Date();
        //토큰 만료시간 60분
        long TOKEN_TIME = 60 * 60 * 1000;
        return BEARER_PREFIX +
            Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }
}
