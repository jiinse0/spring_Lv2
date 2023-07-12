package com.sparta.post.dto;

import com.sparta.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private String username;
    private String title;
    private String content;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.modifiedAt = post.getModifiedAt();
        this.createdAt = post.getCreatedAt();
    }
}
