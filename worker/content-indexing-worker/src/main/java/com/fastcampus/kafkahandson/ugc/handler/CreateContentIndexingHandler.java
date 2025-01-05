package com.fastcampus.kafkahandson.ugc.handler;

import com.fastcampus.kafkahandson.ugc.PostIndexingUsecase;
import com.fastcampus.kafkahandson.ugc.adapter.common.OperationType;
import com.fastcampus.kafkahandson.ugc.adapter.inspectedpost.InspectedPostMessage;
import com.fastcampus.kafkahandson.ugc.adapter.inspectedpost.InspectedPostMessageConverter;
import com.fastcampus.kafkahandson.ugc.inspectedpost.InspectedPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateContentIndexingHandler implements ContentIndexingHandler {

    private final PostIndexingUsecase postIndexingUsecase;

    @Override
    public OperationType getOperationType() {
        return OperationType.CREATE;
    }

    @Override
    public void handle(InspectedPostMessage message) {
        InspectedPost post = InspectedPostMessageConverter.toModel(message);
        postIndexingUsecase.save(post);
    }
}
