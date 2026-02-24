package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record PostCreateDto(
        @NotBlank(message = "제목은 필수입니다.") @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.") String title,

        String content) {
    public Post toEntity(Long id) {
        return new Post(
                id,
                this.title,
                this.content,
                LocalDateTime.now(),
                null,
                0);
    }
}
