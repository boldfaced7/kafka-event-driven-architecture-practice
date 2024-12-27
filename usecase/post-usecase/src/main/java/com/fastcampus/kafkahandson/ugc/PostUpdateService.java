package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostUpdateService implements PostUpdateUsecase {

    private final PostPort postPort;
    private final OriginalPostMessageProducePort originalPostMessageProducePort;

    @Override
    @Transactional
    public Post update(Request request) {
        return postPort.findById(request.postId())
                .map(post -> post.update(
                        request.title(),
                        request.content(),
                        request.categoryId()
                ))
                .map(postPort::save)
                .map(originalPostMessageProducePort::sendUpdateMessage)
                .orElseThrow(RuntimeException::new);
    }
}
