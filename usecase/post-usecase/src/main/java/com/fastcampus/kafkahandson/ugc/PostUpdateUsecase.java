package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.Data;

public interface PostUpdateUsecase {

    Post update(Request request);

    record Request(
            Long postId,
            String title,
            String content,
            Long categoryId) {}
}
