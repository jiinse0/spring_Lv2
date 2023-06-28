package com.sparta.post.repository;

import com.sparta.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // ModifiedAt 필드 데이터를 기준으로 정렬해서 전체데이터를 내보냄
    // 그 기준은 내림차순
    List<Post> findAllByOrderByModifiedAtDesc();
}