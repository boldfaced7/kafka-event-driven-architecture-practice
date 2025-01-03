package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.ResolvedPost;

import java.util.List;
import java.util.Optional;

public interface ResolvedPostCachePort {
    Optional<ResolvedPost> get(Long postId);
    List<ResolvedPost> getAll(List<Long> postIds);
    void set(ResolvedPost resolvedPost);
    void delete(Long postId);
}
