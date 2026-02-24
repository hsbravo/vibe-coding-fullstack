package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDto(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer views,
        List<String> tags) {
    public static PostResponseDto from(Post post) {
        if (post == null) return null;
        return from(post, List.of());
    }

    public static PostResponseDto from(Post post, List<String> tags) {
        if (post == null) return null;
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getViews(),
                tags == null ? List.of() : tags);
    }
}
