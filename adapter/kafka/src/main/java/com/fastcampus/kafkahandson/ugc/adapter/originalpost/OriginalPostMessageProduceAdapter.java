package com.fastcampus.kafkahandson.ugc.adapter.originalpost;

import com.fastcampus.kafkahandson.ugc.CustomObjectMapper;
import com.fastcampus.kafkahandson.ugc.OriginalPostMessageProducePort;
import com.fastcampus.kafkahandson.ugc.adapter.common.OperationType;
import com.fastcampus.kafkahandson.ugc.adapter.common.Topic;
import com.fastcampus.kafkahandson.ugc.post.model.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OriginalPostMessageProduceAdapter implements OriginalPostMessageProducePort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CustomObjectMapper objectMapper = new CustomObjectMapper();

    @Override
    public Post sendCreateMessage(Post post) {
        return sendMessage(post, OperationType.CREATE);
    }

    @Override
    public Post sendUpdateMessage(Post post) {
        return sendMessage(post, OperationType.UPDATE);
    }

    @Override
    public Post sendDeleteMessage(Post post) {
        return sendMessage(post, OperationType.DELETE);
    }

    private Post sendMessage(Post post, OperationType operationType) {
        OriginalPostMessage converted = convertToMessage(post, operationType);
        send(converted);
        return post;
    }

    private OriginalPostMessage convertToMessage(Post post, OperationType operationType) {
        return new OriginalPostMessage(
                post.getId(),
                operationType,
                (operationType == OperationType.DELETE)
                        ? null
                        : new OriginalPostMessage.Payload(
                                post.getId(),
                                post.getTitle(),
                                post.getContent(),
                                post.getUserId(),
                                post.getCategoryId(),
                                post.getCreatedAt(),
                                post.getUpdatedAt(),
                                post.getDeletedAt()
                )
        );
    }

    private void send(OriginalPostMessage message) {
        try {
            kafkaTemplate.send(
                    Topic.ORIGINAL_POST,
                    message.getId().toString(),
                    objectMapper.writeValueAsString(message)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
