package com.sparta.letstodogo.filter;

import com.fasterxml.jackson.databind.*;
import com.sparta.letstodogo.*;
import com.sparta.letstodogo.user.*;
import com.sparta.letstodogo.user.UserDetailsService;
import com.sparta.letstodogo.util.*;
import io.jsonwebtoken.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.security.*;
import java.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.filter.*;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request); //Token 뽑아오기 & 넣기 & 검증 / *JwtUtil 작성 필요

        if (Objects.nonNull(token)) {
            if (jwtUtil.validateToken(token)) { //암호화 된 Key값인지 검증
                Claims info = jwtUtil.getUserInfoFromToken(token);

                // 인증정보에 유저정보(username) 넣기
                // username -> user 조회
                String username = info.getSubject();
                SecurityContext context = SecurityContextHolder.createEmptyContext();//보안관련ContextHolder에 빈 Context생성
                // -> userDetails 에 담고
                UserDetails userDetails = userDetailsService.getUserDetails(
                    username); //이제 Authentication 생성 가능
                // -> authentication의 principal 에 담고
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null); //credential 신임장
                // -> securityContent 에 담고
                context.setAuthentication(authentication);
                // -> SecurityContextHolder 에 담고
                SecurityContextHolder.setContext(context);
                // -> 이제 @AuthenticationPrincipal 로 조회할 수 있음
            } else {
                //인증정보가 존재하지 않을 때
                CommonResponseDto commonResponseDto = new CommonResponseDto("토큰이 유효하지 않잖아요!",
                    HttpStatus.BAD_REQUEST.value());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json; charset=UTF-8"); //body가 깨지지 않도록
                response.getWriter().write(objectMapper.writeValueAsString(
                    commonResponseDto)); //객체 그대로 문자열로 넣을 수 없기 때문에 ObjectMapper 사용 => 응답 body가 Json형태 문자열로 들어감
            }
        }
        //login처리 후 다음 filter로 방향제시
        filterChain.doFilter(request, response);
    }
}
