package com.fastcampus.kafkahandson.ugc.handler;

import com.fastcampus.kafkahandson.ugc.PostIndexingUsecase;
import com.fastcampus.kafkahandson.ugc.adapter.common.OperationType;
import com.fastcampus.kafkahandson.ugc.adapter.inspectedpost.InspectedPostMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteContentIndexingHandler implements ContentIndexingHandler {

    private final PostIndexingUsecase postIndexingUsecase;

    @Override
    public OperationType getOperationType() {
        return OperationType.DELETE;
    }

    @Override
    public void handle(InspectedPostMessage message) {
        postIndexingUsecase.delete(message.id());
    }
}
