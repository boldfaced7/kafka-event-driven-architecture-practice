package com.fastcampus.kafkahandson.ugc.model;

import java.time.LocalDateTime;

public record PostDetailResponse(
        Long id,
        String title,
        String content,
        String userName,
        String categoryName,
        LocalDateTime createdAt,
        Boolean updated
) {
}
