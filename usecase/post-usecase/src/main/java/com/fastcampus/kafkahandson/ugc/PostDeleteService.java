package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostDeleteService implements PostDeleteUsecase {

    private final PostPort postPort;
    private final OriginalPostMessageProducePort originalPostMessageProducePort;

    @Override
    @Transactional
    public Post delete(Request request) {
        return postPort.findById(request.postId())
                .map(Post::delete)
                .map(postPort::save)
                .map(originalPostMessageProducePort::sendDeleteMessage)
                .orElseThrow(RuntimeException::new);
    }
}
