package com.fastcampus.kafkahandson.ugc.consumer;

import com.fastcampus.kafkahandson.ugc.CustomObjectMapper;
import com.fastcampus.kafkahandson.ugc.adapter.common.OperationType;
import com.fastcampus.kafkahandson.ugc.adapter.common.Topic;
import com.fastcampus.kafkahandson.ugc.adapter.inspectedpost.InspectedPostMessage;
import com.fastcampus.kafkahandson.ugc.handler.ContentIndexingHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ContentIndexingWorker {

    private final CustomObjectMapper objectMapper = new CustomObjectMapper();
    private final Map<OperationType, ContentIndexingHandler> handlers;

    public ContentIndexingWorker(List<ContentIndexingHandler> list) {
        handlers = list.stream()
                .collect(Collectors.toMap(
                        ContentIndexingHandler::getOperationType,
                        Function.identity()
                ));
    }

    @KafkaListener(
            topics = {Topic.INSPECTED_POST},
            groupId = "indexing-post-consumer-group",
            concurrency = "3"
    )
    public void listen(ConsumerRecord<String, String> message) {
        InspectedPostMessage inspectedPostMessage = toMessage(message.value());
        OperationType operationType = inspectedPostMessage.operationType();

        Optional.ofNullable(handlers.get(operationType))
                .ifPresent(handler -> handler.handle(inspectedPostMessage));
    }

    private InspectedPostMessage toMessage(String content) {
        try {
            return objectMapper.readValue(
                    content,
                    InspectedPostMessage.class
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
