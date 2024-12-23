package com.fastcampus.kafkahandson.ugc.model;

public record PostUpdateRequest(
        String title,
        String content,
        Long categoryId
) {
}
