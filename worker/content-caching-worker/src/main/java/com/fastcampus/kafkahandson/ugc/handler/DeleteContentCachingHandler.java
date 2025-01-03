package com.fastcampus.kafkahandson.ugc.handler;

import com.fastcampus.kafkahandson.ugc.ResolvedPostUncacheUsecase;
import com.fastcampus.kafkahandson.ugc.adapter.common.OperationType;
import com.fastcampus.kafkahandson.ugc.adapter.originalpost.OriginalPostMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteContentCachingHandler implements ContentCachingHandler {

    private final ResolvedPostUncacheUsecase resolvedPostUncacheUsecase;

    @Override
    public OperationType getOperationType() {
        return OperationType.DELETE;
    }

    @Override
    public void handle(OriginalPostMessage message) {
        resolvedPostUncacheUsecase.delete(message.getId());
    }
}
