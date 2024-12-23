package com.fastcampus.kafkahandson.ugc.controller;

import com.fastcampus.kafkahandson.ugc.*;
import com.fastcampus.kafkahandson.ugc.model.PostCreateRequest;
import com.fastcampus.kafkahandson.ugc.model.PostDetailResponse;
import com.fastcampus.kafkahandson.ugc.model.PostResponse;
import com.fastcampus.kafkahandson.ugc.model.PostUpdateRequest;
import com.fastcampus.kafkahandson.ugc.post.model.Post;
import com.fastcampus.kafkahandson.ugc.post.model.ResolvedPost;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostCreateUsecase postCreateUsecase;
    private final PostReadUsecase postReadUsecase;
    private final PostUpdateUsecase postUpdateUsecase;
    private final PostDeleteUsecase postDeleteUsecase;

    @PostMapping
    ResponseEntity<PostResponse> createPost(
            @RequestBody PostCreateRequest request
    ) {
        Post post = postCreateUsecase.create(toCreateUsecaseRequest(request));
        return ResponseEntity.ok().body(toResponse(post));
    }

    @GetMapping("/{postId}")
    ResponseEntity<PostDetailResponse> readPostDetail(
            @PathVariable("postId") Long postId
    ) {
        ResolvedPost resolvedPost = postReadUsecase.getById(postId);
        return ResponseEntity.ok().body(toResponse(resolvedPost));
    }

    @PutMapping("/{postId}")
    ResponseEntity<PostResponse> updatePost(
            @PathVariable("postId") Long postId,
            @RequestBody PostUpdateRequest request
    ) {
        Post post = postUpdateUsecase.update(toUpdateUsecaseRequest(request, postId));
        return ResponseEntity.ok().body(toResponse(post));
    }

    @DeleteMapping("{postId}")
    ResponseEntity<PostResponse> deletePost(
            @PathVariable("postId") Long postId
    ) {
        Post post = postDeleteUsecase.delete(toDeleteUsecaseRequest(postId));
        return ResponseEntity.ok().body(toResponse(post));
    }

    private PostCreateUsecase.Request toCreateUsecaseRequest(PostCreateRequest request) {
        return new PostCreateUsecase.Request(
                request.userId(),
                request.title(),
                request.content(),
                request.categoryId()
        );
    }

    private PostUpdateUsecase.Request toUpdateUsecaseRequest(PostUpdateRequest request, Long PostId) {
        return new PostUpdateUsecase.Request(
                PostId,
                request.title(),
                request.content(),
                request.categoryId()
        );
    }

    private PostDeleteUsecase.Request toDeleteUsecaseRequest(Long postId) {
        return new PostDeleteUsecase.Request(
                postId
        );
    }

    private PostResponse toResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUserId(),
                post.getCategoryId(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getDeletedAt()
        );
    }

    private PostDetailResponse toResponse(ResolvedPost resolvedPost) {
        return new PostDetailResponse(
                resolvedPost.getId(),
                resolvedPost.getTitle(),
                resolvedPost.getContent(),
                resolvedPost.getUserName(),
                resolvedPost.getCategoryName(),
                resolvedPost.getCreatedAt(),
                resolvedPost.isUpdated()
        );
    }
}
