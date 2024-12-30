package com.fastcampus.kafkahandson.ugc.handler;

import com.fastcampus.kafkahandson.ugc.SubscribingPostAddToInboxUsecase;
import com.fastcampus.kafkahandson.ugc.adapter.common.OperationType;
import com.fastcampus.kafkahandson.ugc.adapter.inspectedpost.InspectedPostMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateContentSubscribingHandler implements ContentSubscribingHandler {

    private final SubscribingPostAddToInboxUsecase subscribingPostAddToInboxUsecase;
    @Override
    public OperationType getOperationType() {
        return OperationType.CREATE;
    }

    @Override
    public void handle(InspectedPostMessage message) {
        subscribingPostAddToInboxUsecase.saveToSubscribingInboxPost(
                message.payload().post()
        );
    }
}
