package com.fastcampus.kafkahandson.ugc.handler;

import com.fastcampus.kafkahandson.ugc.adapter.common.OperationType;
import com.fastcampus.kafkahandson.ugc.adapter.inspectedpost.InspectedPostMessage;

public interface ContentIndexingHandler {
    OperationType getOperationType();
    void handle(InspectedPostMessage message);
}
