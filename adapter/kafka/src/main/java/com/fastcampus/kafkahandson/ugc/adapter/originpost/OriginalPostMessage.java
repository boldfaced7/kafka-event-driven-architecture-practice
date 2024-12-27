package com.fastcampus.kafkahandson.ugc.adapter.originpost;

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
            LocalDateTime createdAd,
            LocalDateTime updatedAd,
            LocalDateTime deletedAd
    ) {}
}
