package com.fastcampus.kafkahandson.ugc.model;

public record PostCreateRequest(
        String title,
        String content,
        Long userId,
        Long categoryId
) {
}
