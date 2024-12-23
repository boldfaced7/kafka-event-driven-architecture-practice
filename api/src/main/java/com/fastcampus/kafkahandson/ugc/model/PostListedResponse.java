package com.fastcampus.kafkahandson.ugc.model;

import java.time.LocalDateTime;

public record PostListedResponse(
        Long id,
        String title,
        String content,
        String userName,
        LocalDateTime createdAt
) {
}
