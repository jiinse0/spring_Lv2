package com.sparta.post.service;

import com.sparta.post.dto.PostRequestDto;
import com.sparta.post.dto.PostResponseDto;
import com.sparta.post.entity.Post;
import com.sparta.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostResponseDto> getPost() {
        return postRepository.findAllByOrderByModifiedAtDesc()
                .stream()
                .map(PostResponseDto::new)
                .toList();
    }

    public PostResponseDto createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);

        Post savePost = postRepository.save(post);

        PostResponseDto postResponseDto = new PostResponseDto(post);

        return postResponseDto;
    }

    public Long selectPost(Long id, PostRequestDto requestDto) {

        Post post = findPost(id);

        post.selectPost(requestDto);

        return id;
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = findPost(id);

        if (post.checkPassword(requestDto.getPassword())) {
            post.update(requestDto);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return new PostResponseDto(post);
    }

    @Transactional
    public String deletePost(Long id, String password) {
        Post post = findPost(id);

        if (post.checkPassword(password)) {
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return "삭제가 완료 되었습니다.";
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}