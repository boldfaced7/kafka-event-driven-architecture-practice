package com.fastcampus.kafkahandson.ugc.adapter.originalpost;

import com.fastcampus.kafkahandson.ugc.post.model.Post;

public class OriginalPostMessageConverter {

    public static Post toModel(OriginalPostMessage message) {
        return new Post(
                message.getPayload().id(),
                message.getPayload().title(),
                message.getPayload().content(),
                message.getPayload().userId(),
                message.getPayload().categoryId(),
                message.getPayload().createdAt(),
                message.getPayload().updatedAt(),
                message.getPayload().deletedAt()
        );
    }
}
