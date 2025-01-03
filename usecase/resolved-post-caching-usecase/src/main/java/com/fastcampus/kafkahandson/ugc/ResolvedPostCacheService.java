package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;
import com.fastcampus.kafkahandson.ugc.post.model.ResolvedPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResolvedPostCacheService implements ResolvedPostCacheUsecase {

    private final MetadataPort metadataPort;
    private final ResolvedPostCachePort resolvedPostCachePort;

    @Override
    public void save(Post post) {
        Optional.ofNullable(post)
                .map(this::generateResolvedPost)
                .ifPresent(resolvedPostCachePort::set);

    }

    private ResolvedPost generateResolvedPost(Post post) {
        return ResolvedPost.generate(
                post,
                metadataPort.getUserNameByUserId(post.getUserId()),
                metadataPort.getCategoryNameByCategoryId(post.getCategoryId())
        );
    }
}
