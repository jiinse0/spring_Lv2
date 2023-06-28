package com.sparta.post.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String userName;
    private String password;
    private String title;
    private String content;
}