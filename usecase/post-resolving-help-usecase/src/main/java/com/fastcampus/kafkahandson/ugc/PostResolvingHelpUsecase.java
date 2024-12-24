package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.ResolvedPost;

import java.util.List;

public interface PostResolvingHelpUsecase {

    ResolvedPost resolvePostById(Long postId); // 컨텐츠 상세 페이지

    List<ResolvedPost> resolvePostsByIds(List<Long> postIds); // 컨텐츠 목록(구독, 검색 페이지)
}
