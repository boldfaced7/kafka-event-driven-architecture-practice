package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.aspect.CacheableResolvedPost;
import com.fastcampus.kafkahandson.ugc.aspect.CacheableResolvedPostList;
import com.fastcampus.kafkahandson.ugc.post.model.Post;
import com.fastcampus.kafkahandson.ugc.post.model.ResolvedPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostResolvingHelpService implements PostResolvingHelpUsecase {

    private final PostPort postPort;
    private final MetadataPort metadataPort;

    @Override
    @CacheableResolvedPost(key = "#postId")
    public ResolvedPost resolvePostById(Long postId) {
        return postPort.findById(postId)
                .map(this::generateResolvedPost)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    @CacheableResolvedPostList(keys = "#postIds")
    public List<ResolvedPost> resolvePostsByIds(List<Long> postIds) {
        return postIds.stream().map(this::resolvePostById).toList();
    }

    private ResolvedPost generateResolvedPost(Post post) {
        return ResolvedPost.generate(
                post,
                metadataPort.getUserNameByUserId(post.getUserId()),
                metadataPort.getCategoryNameByCategoryId(post.getCategoryId())
        );
    }
}
