package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribingPostAddToInboxService implements SubscribingPostAddToInboxUsecase {

    private final SubscribingPostPort subscribingPostPort;
    private final MetadataPort metadataPort;

    @Override
    public void saveToSubscribingInboxPost(Post post) {
        List<Long> followerUserIds = metadataPort.listFollowerIdsByUserId(post.getUserId());
        subscribingPostPort.addToPostFollowerInboxes(post, followerUserIds);
    }
}
