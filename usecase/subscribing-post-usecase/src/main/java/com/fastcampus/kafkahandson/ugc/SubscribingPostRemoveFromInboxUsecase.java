package com.fastcampus.kafkahandson.ugc;

public interface SubscribingPostRemoveFromInboxUsecase {
    void deleteFromSubscribingInboxPost(Long postId);
}
