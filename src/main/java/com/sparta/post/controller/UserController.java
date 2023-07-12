package com.sparta.post.controller;

import com.sparta.post.dto.AuthRequestDto;
import com.sparta.post.dto.StatusResponseDto;
import com.sparta.post.jwt.JwtUtil;
import com.sparta.post.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final JwtUtil jwtUtil;

    // 회원가입
    @PostMapping("/auth/signup")
    public StatusResponseDto signup(@Valid @RequestBody AuthRequestDto requestDto) {

        userService.signup(requestDto);

        return new StatusResponseDto("회원가입이 완료되었습니다.", HttpStatus.CREATED.value());
    }

    // 로그인
    @PostMapping("/auth/login")
    // HttpServletResponse : 서블릿 컨테이너에서 클라이언트로부터 받은 HTTP 요청에 대한 응답을 생성하고 전송하기 위한 객체
    public StatusResponseDto login(@RequestBody AuthRequestDto requestDto, HttpServletResponse response) {

        userService.login(requestDto);

        /*
        JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        response.addHeader() : response 객체에 새로운 헤더를 추가
                               JwtUtil.AUTHORIZATION_HEADER에 정의된 헤더 이름과 jwtUtil.createToken()에서 생성한 JWT 토큰 값을 사용하여 헤더를 설정
                               HTTP 응답에 JWT 토큰이 포함된 헤더가 추가

        JwtUtil.AUTHORIZATION_HEADER : JwtUtil 클래스에 있는 AUTHORIZATION_HEADER라는 상수값을 참조.
        AUTHORIZATION_HEADER : JWT 토큰을 HTTP 요청의 헤더에 추가할 때 사용되는 헤더 이름
        jwtUtil.createToken(loginRequestDto.getUsername()) : jwtUtil 객체를 사용하여 AuthRequestDto에서 가져온 사용자 이름을 기반으로 JWT 토큰 생성
        */
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(requestDto.getUsername()));

        return new StatusResponseDto("로그인이 완료되었습니다.", HttpStatus.OK.value());
    }
}
