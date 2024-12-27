package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;

public class PostConverter {

    public static PostJpaEntity toJpaEntity(Post post) {
        return new PostJpaEntity(
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

    public static Post toModel(PostJpaEntity postJpaEntity) {
        return new Post(
                postJpaEntity.getId(),
                postJpaEntity.getTitle(),
                postJpaEntity.getContent(),
                postJpaEntity.getUserId(),
                postJpaEntity.getCategoryId(),
                postJpaEntity.getCreatedAt(),
                postJpaEntity.getUpdatedAt(),
                postJpaEntity.getDeletedAt()
        );
    }
}
