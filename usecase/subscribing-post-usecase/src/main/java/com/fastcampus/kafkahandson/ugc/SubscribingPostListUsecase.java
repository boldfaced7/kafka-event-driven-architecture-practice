package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.ResolvedPost;

import java.util.List;

public interface SubscribingPostListUsecase {
    List<ResolvedPost> listSubscribingInboxPosts(Request request);

    record Request(
            int pageNumber,
            Long followerUserId
    ) {}
}
