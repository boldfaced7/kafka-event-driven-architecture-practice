package com.fastcampus.kafkahandson.ugc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResolvedPostUncacheService implements ResolvedPostUncacheUsecase {

    private final ResolvedPostCachePort resolvedPostCachePort;

    @Override
    public void delete(Long postId) {
        resolvedPostCachePort.delete(postId);
    }
}
