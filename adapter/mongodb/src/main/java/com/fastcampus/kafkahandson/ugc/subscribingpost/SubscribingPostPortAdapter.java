package com.fastcampus.kafkahandson.ugc.subscribingpost;

import com.fastcampus.kafkahandson.ugc.SubscribingPostPort;
import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SubscribingPostPortAdapter implements SubscribingPostPort {

    private final SubscribingPostRepository subscribingPostRepository;

    @Override
    public void addToPostFollowerInboxes(Post post, List<Long> followerUserIds) {
        List<SubscribingPostDocument> documents = followerUserIds
                .stream()
                .map(followerUserId -> SubscribingPostDocument
                        .generate(
                                post,
                                followerUserId
                        ))
                .toList();
        subscribingPostRepository.saveAll(documents);
    }

    @Override
    public void removePostFromFollowerInboxes(Long postId) {
        subscribingPostRepository.deleteAllByPostId(postId);
    }

    @Override
    public List<Long> listPostIdsByFollowerUserId(Long followerUserId, int pageNumber, int pageSize) {
        return subscribingPostRepository.findByFollowerUserId(followerUserId, pageNumber, pageSize)
                .stream()
                .map(SubscribingPostDocument::getPostId)
                .toList();
    }
}
