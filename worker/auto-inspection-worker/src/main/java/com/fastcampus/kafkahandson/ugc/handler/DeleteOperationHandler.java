package com.fastcampus.kafkahandson.ugc.handler;

import com.fastcampus.kafkahandson.ugc.InspectedPostMessageProducePort;
import com.fastcampus.kafkahandson.ugc.PostInspectUsecase;
import com.fastcampus.kafkahandson.ugc.adapter.common.OperationType;
import com.fastcampus.kafkahandson.ugc.adapter.originalpost.OriginalPostMessage;
import com.fastcampus.kafkahandson.ugc.adapter.originalpost.OriginalPostMessageConverter;
import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteOperationHandler implements OperationHandler {

    private final PostInspectUsecase postInspectUsecase;
    private final InspectedPostMessageProducePort inspectedPostMessageProducePort;

    @Override
    public OperationType getOperationType() {
        return OperationType.DELETE;
    }

    @Override
    public void handle(OriginalPostMessage message) {
        Post received = OriginalPostMessageConverter.toModel(message);
        postInspectUsecase.inspect(received)
                .ifPresent(inspectedPostMessageProducePort::sendDeleteMessage);
    }
}
