package com.fastcampus.kafkahandson.ugc.controller;

import com.fastcampus.kafkahandson.ugc.SubscribingPostListUsecase;
import com.fastcampus.kafkahandson.ugc.model.PostListedResponse;
import com.fastcampus.kafkahandson.ugc.post.model.ResolvedPost;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostListController {

    private final SubscribingPostListUsecase subscribingPostListUsecase;

    @GetMapping("/inboxes/{userId}")
    ResponseEntity<List<PostListedResponse>> getSubscribingPosts(
            @PathVariable Long userId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page
    ) {
        return subscribingPostListUsecase
                .listSubscribingInboxPosts(toRequest(page, userId))
                .stream()
                .map(this::toResponse)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        ResponseEntity::ok
                ));
    }
    @GetMapping("/search")
    ResponseEntity<List<PostListedResponse>> searchPosts(
            @RequestParam("query") String query
    ) {
        return ResponseEntity.internalServerError().build();
    }

    private SubscribingPostListUsecase.Request toRequest(int page, Long userId) {
        return new SubscribingPostListUsecase.Request(
                page,
                userId
        );
    }

    private PostListedResponse toResponse(ResolvedPost resolvedPost) {
        return new PostListedResponse(
                resolvedPost.getId(),
                resolvedPost.getTitle(),
                resolvedPost.getContent(),
                resolvedPost.getUserName(),
                resolvedPost.getCreatedAt()
        );
    }
}
