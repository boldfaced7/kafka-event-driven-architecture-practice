package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;

import java.util.Optional;

public interface PostPort {
    Post save(Post post);
    Optional<Post> findById(Long id);
}
