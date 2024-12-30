package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;

import java.util.List;

public interface SubscribingPostPort {
    void addToPostFollowerInboxes(Post post, List<Long> followerUserIds);
    void removePostFromFollowerInboxes(Long postId);
    List<Long> listPostIdsByFollowerUserId(Long followerUserId, int pageNumber, int pageSize);
}
