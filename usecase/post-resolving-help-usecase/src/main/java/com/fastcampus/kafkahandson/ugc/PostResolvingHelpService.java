package com.fastcampus.kafkahandson.ugc;

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
    public ResolvedPost resolvePostById(Long postId) {
        return postPort.findById(postId)
                .map(post -> ResolvedPost.generate(
                        post,
                        metadataPort.getUserNameByUserId(post.getUserId()),
                        metadataPort.getCategoryNameByCategoryId(post.getCategoryId())
                ))
                .orElseThrow(RuntimeException:: new);
    }

    @Override
    public List<ResolvedPost> resolvePostsByIds(List<Long> postIds) {
        // TODO: 임시이므로 수정 필요
        return postIds.stream().map(this::resolvePostById).toList();
    }
}
