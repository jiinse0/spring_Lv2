package com.sparta.post.dto;

import lombok.Getter;

@Getter
public class StatusResponseDto {
    private String msg;
    private Integer StatusCode;

    public StatusResponseDto(String msg, Integer StatusCode) {
        this.msg = msg;
        this.StatusCode = StatusCode;
    }
}
