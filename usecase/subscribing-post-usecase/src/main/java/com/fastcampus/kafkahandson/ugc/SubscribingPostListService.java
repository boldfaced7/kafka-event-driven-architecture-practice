package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.ResolvedPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribingPostListService implements SubscribingPostListUsecase {

    private static final int PAGE_SIZE = 5;

    private final SubscribingPostPort subscribingPostPort;
    private final PostResolvingHelpUsecase postResolvingHelpUsecase;

    @Override
    public List<ResolvedPost> listSubscribingInboxPosts(Request request) {
        List<Long> subscribingPostIds = subscribingPostPort.listPostIdsByFollowerUserId(
                request.followerUserId(),
                request.pageNumber(),
                PAGE_SIZE
        );
        return postResolvingHelpUsecase.resolvePostsByIds(subscribingPostIds);
    }
}
