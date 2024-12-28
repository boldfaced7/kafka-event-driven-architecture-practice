package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.inspectedpost.InspectedPost;
import com.fastcampus.kafkahandson.ugc.post.model.Post;

import java.util.Optional;

public interface PostInspectUsecase {
    Optional<InspectedPost> inspect(Post post);
}
