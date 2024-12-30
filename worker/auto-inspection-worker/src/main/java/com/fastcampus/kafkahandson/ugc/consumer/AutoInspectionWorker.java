package com.fastcampus.kafkahandson.ugc.consumer;

import com.fastcampus.kafkahandson.ugc.CustomObjectMapper;
import com.fastcampus.kafkahandson.ugc.adapter.common.OperationType;
import com.fastcampus.kafkahandson.ugc.adapter.common.Topic;
import com.fastcampus.kafkahandson.ugc.adapter.originalpost.OriginalPostMessage;
import com.fastcampus.kafkahandson.ugc.handler.AutoInspectionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AutoInspectionWorker {
    private final CustomObjectMapper objectMapper = new CustomObjectMapper();
    private final Map<OperationType, AutoInspectionHandler> operationHandlers;

    public AutoInspectionWorker(List<AutoInspectionHandler> list) {
        operationHandlers = list.stream()
                .collect(Collectors.toMap(
                        AutoInspectionHandler::getOperationType,
                        Function.identity()
                ));
    }

    @KafkaListener(
            topics = {Topic.ORIGINAL_POST},
            groupId = "auto-inspection-consumer-group",
            concurrency = "3"
    )
    public void listen(ConsumerRecord<String, String> message) {
        OriginalPostMessage originalPostMessage = readMessage(message.value());
        operationHandlers.get(originalPostMessage.getOperationType())
                .handle(originalPostMessage);
    }

    private OriginalPostMessage readMessage(String content) {
        try {
            return objectMapper.readValue(
                    content,
                    OriginalPostMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
