package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostUpdateService implements PostUpdateUsecase {

    private final PostPort postPort;
    @Override
    @Transactional
    public Post update(Request request) {
        Post updated = postPort.findById(request.postId())
                .map(post -> post.update(
                        request.title(),
                        request.content(),
                        request.categoryId()
                ))
                .orElseThrow(RuntimeException::new);
        return postPort.save(updated);
    }
}
