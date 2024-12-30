package com.fastcampus.kafkahandson.ugc.handler;

import com.fastcampus.kafkahandson.ugc.adapter.common.OperationType;
import com.fastcampus.kafkahandson.ugc.adapter.originalpost.OriginalPostMessage;

public interface OperationHandler {
    OperationType getOperationType();

    void handle(OriginalPostMessage message);
}
