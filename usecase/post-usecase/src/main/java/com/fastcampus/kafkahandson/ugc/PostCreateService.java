package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCreateService implements PostCreateUsecase {

    private final PostPort postPort;
    private final OriginalPostMessageProducePort originalPostMessageProducePort;

    @Override
    @Transactional
    public Post create(Request request) {
        Post saved = postPort.save(
                Post.generate(
                        request.userId(),
                        request.title(),
                        request.content(),
                        request.categoryId()
                )
        );
        return originalPostMessageProducePort.sendCreateMessage(saved);
    }
}
