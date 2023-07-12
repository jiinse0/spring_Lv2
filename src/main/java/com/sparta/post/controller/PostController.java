package com.sparta.post.controller;

import com.sparta.post.dto.PostRequestDto;
import com.sparta.post.dto.PostResponseDto;
import com.sparta.post.dto.StatusResponseDto;
import com.sparta.post.entity.User;
import com.sparta.post.security.UserDetailsImpl;
import com.sparta.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService service;

    // 게시글 생성
    @PostMapping("/post")
    // @AuthenticationPrincipal UserDetailsImpl userDetails : 현재 인증된 사용자의 세부 정보를 주입 (로그인된 사용자의 정보 게시글에 넣기)
    // @AuthenticationPrincipal : 현재 인증된 사용자의 UserDetails 객체를 주입받기 위해 사용
    public PostResponseDto createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto requestDto) {

        return service.createPost(requestDto, userDetails.getUser());
    }

    // 게시글 선택 조회
    @GetMapping("/post/{id}")
    public PostResponseDto getOnePost(@PathVariable Long id) {
        return service.getOnePost(id);
    }

    // 게시글 조회
    @GetMapping("/post")
    public List<PostResponseDto> getPost() {
        return service.getPost();
    }

    // 게시글 수정
    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody PostRequestDto requestDto) {


        return service.updatePost(id, requestDto, userDetails.getUser());
    }

    // 게시글 삭제
    @DeleteMapping("/post/{id}")
    public StatusResponseDto deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        service.deletePost(id, requestDto, userDetails.getUser());

        return new StatusResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
    }
}