package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;

public interface OriginalPostMessageProducePort {
    Post sendCreateMessage(Post post);
    Post sendUpdateMessage(Post post);
    Post sendDeleteMessage(Post post);
}
