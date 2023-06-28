package com.sparta.post.dto;

import com.sparta.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private String userName;
    private String title;
    private String content;
    private String password;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.userName = post.getUserName();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.password = post.getPassword();
        this.id = post.getId();
        this.modifiedAt = post.getModifiedAt();
        this.createdAt = post.getCreatedAt();
    }
}
