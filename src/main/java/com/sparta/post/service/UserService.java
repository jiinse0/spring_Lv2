package com.sparta.post.service;

import com.sparta.post.dto.AuthRequestDto;
import com.sparta.post.entity.User;
import com.sparta.post.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(AuthRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        /*isPresent : Optional 객체가 값(Non-null)을 포함하고 있는지 여부를 확인하는 메서드
        값이 존재하는 경우 true를 반환하고, 값이 없는 경우(null 또는 Optional.empty()) false를 반환*/
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        User user = new User(username, password);
        userRepository.save(user);
    }

    public void login(AuthRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        //passwordEncoder.matches()를 사용하면 인코딩된 비밀번호와 주어진 비밀번호가 일치하는지 확인할 수 있다.
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
