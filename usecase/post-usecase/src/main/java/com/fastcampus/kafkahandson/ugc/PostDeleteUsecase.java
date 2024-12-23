package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;

public interface PostDeleteUsecase {

    Post delete(Request request);

    record Request(
            Long postId
    ) {}
}
