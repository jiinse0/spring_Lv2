package com.sparta.post.service;

import com.sparta.post.dto.PostRequestDto;
import com.sparta.post.dto.PostResponseDto;
import com.sparta.post.entity.Post;
import com.sparta.post.entity.User;
import com.sparta.post.repository.PostRepository;
import com.sparta.post.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        // RequestDto -> Entity
        Post post = new Post(requestDto, user);

        // DB 저장
        postRepository.save(post);

        return new PostResponseDto(post);
    }

    public PostResponseDto getOnePost(Long id) {
        Post post = findByPost(id);

        return new PostResponseDto(post);
    }

    public List<PostResponseDto> getPost() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream()
                .map(PostResponseDto::new)
                .toList();
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {

        String password = requestDto.getPassword();

        Post post = findByPost(id);

        if (!requestDto.getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("사용자가 일치하지 않습니다.");
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            post.update(requestDto);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return new PostResponseDto(post);
    }

    public void deletePost(Long id, PostRequestDto requestDto, User user) {

        String password = requestDto.getPassword();

        Post post = findByPost(id);

        checkUsername(requestDto.getUsername(), user.getUsername());

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        postRepository.delete(post);
    }

    private Post findByPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );
    }

    private void checkUsername(String requestDtoUsername, String username) {
        if (!requestDtoUsername.equals(username)) {
            throw new IllegalArgumentException("사용자가 일치하지 않습니다.");
        }
    }
}