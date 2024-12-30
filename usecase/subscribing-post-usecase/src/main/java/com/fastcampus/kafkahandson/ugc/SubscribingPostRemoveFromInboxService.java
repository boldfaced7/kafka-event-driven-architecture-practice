package com.fastcampus.kafkahandson.ugc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribingPostRemoveFromInboxService implements SubscribingPostRemoveFromInboxUsecase {
    private final SubscribingPostPort subscribingPostPort;

    @Override
    public void deleteFromSubscribingInboxPost(Long postId) {
        subscribingPostPort.removePostFromFollowerInboxes(postId);
    }
}
