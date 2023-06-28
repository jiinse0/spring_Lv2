package com.sparta.post.entity;

import com.sparta.post.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userName", nullable = false)
    private String userName;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "password", nullable = false)
    private String password;

    public Post(PostRequestDto RequestDto) {
        this.userName = RequestDto.getUserName();
        this.title = RequestDto.getTitle();
        this.content = RequestDto.getContent();
        this.password = RequestDto.getPassword();
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.userName = requestDto.getUserName();
        this.content = requestDto.getContent();
    }

    public void selectPost(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.userName = requestDto.getUserName();
        this.content = requestDto.getContent();
    }

    public boolean checkPassword(String savePassword) {
        return password.equals(savePassword);
    }
}
