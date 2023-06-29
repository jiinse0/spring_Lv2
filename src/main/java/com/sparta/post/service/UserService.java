package com.sparta.post.service;

import com.sparta.post.dto.LoginRequestDto;
import com.sparta.post.dto.SignupRequestDto;
import com.sparta.post.dto.UserResponseDto;
import com.sparta.post.entity.User;
import com.sparta.post.jwt.JwtUtil;
import com.sparta.post.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 회원가입
    @Transactional
    public UserResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // id 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        userRepository.save(new User(username, password));

        // 회원가입 완료 로그 출력
        log.info("회원가입이 완료되었습니다.");

        UserResponseDto userResponseDto = new UserResponseDto("회원가입이 완료 되었습니다.", HttpStatus.OK.value());
        return userResponseDto;
    }
    // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가

    // 로그인
    @Transactional(readOnly = true)
    public UserResponseDto login(LoginRequestDto requestDto, HttpServletResponse res) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // username 일치 여부 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않은 아이디입니다.")
        );

        // password 확인
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 토큰 생성 및 쿠키에 저장
        res.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));

        // 로그인 성공 로그 출력
        log.info("로그인에 성공하셨습니다.");

        // UserResponseDto 생성 및 반환
        UserResponseDto userResponseDto = new UserResponseDto("로그인 성공하셨습니다.", HttpStatus.OK.value());
        return userResponseDto;
    }
}
