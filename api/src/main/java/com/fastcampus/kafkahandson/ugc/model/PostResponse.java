package com.fastcampus.kafkahandson.ugc.model;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String content,
        Long userId,
        Long categoryId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {
}
