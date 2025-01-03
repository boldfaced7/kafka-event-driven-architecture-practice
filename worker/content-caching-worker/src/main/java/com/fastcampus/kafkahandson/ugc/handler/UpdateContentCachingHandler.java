package com.fastcampus.kafkahandson.ugc.handler;

import com.fastcampus.kafkahandson.ugc.ResolvedPostCacheUsecase;
import com.fastcampus.kafkahandson.ugc.adapter.common.OperationType;
import com.fastcampus.kafkahandson.ugc.adapter.originalpost.OriginalPostMessage;
import com.fastcampus.kafkahandson.ugc.adapter.originalpost.OriginalPostMessageConverter;
import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateContentCachingHandler implements ContentCachingHandler {

    private final ResolvedPostCacheUsecase resolvedPostCacheUsecase;

    @Override
    public OperationType getOperationType() {
        return OperationType.UPDATE;
    }

    @Override
    public void handle(OriginalPostMessage message) {
        Post post = OriginalPostMessageConverter.toModel(message);
        resolvedPostCacheUsecase.save(post);
    }
}
