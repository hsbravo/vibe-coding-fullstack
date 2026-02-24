package com.example.vibeapp.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostUpdateDto(
        Long id,

        @NotBlank(message = "제목은 필수입니다.") @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.") String title,

        String content,

        // 쉼표로 구분된 태그 문자열
        String tagString) {
}
