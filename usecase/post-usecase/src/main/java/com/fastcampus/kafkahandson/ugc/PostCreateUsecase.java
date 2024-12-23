package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;

public interface PostCreateUsecase {

    Post create(Request request);

    record Request(
            Long userId,
            String title,
            String content,
            Long categoryId) {}
}
