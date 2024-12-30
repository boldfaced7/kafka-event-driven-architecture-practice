package com.fastcampus.kafkahandson.ugc.subscribingpost;

import java.util.List;

public interface SubscribingPostCustomRepository {
    List<SubscribingPostDocument> findByFollowerUserId(Long followerUserId, int pageNumber, int pateSize);

    void deleteAllByPostId(Long postId);
}
