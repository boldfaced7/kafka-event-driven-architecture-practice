package com.fastcampus.kafkahandson.ugc.adapter.originalpost;

import com.fastcampus.kafkahandson.ugc.adapter.common.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OriginalPostMessage {
    private Long id;
    private OperationType operationType;
    private Payload payload;

    public record Payload(
            Long id,
            String title,
            String content,
            Long userId,
            Long categoryId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {}
}
