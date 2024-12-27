package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostDeleteService implements PostDeleteUsecase {

    private final PostPort postPort;

    @Override
    @Transactional
    public Post delete(Request request) {
        Post deleted = postPort.findById(request.postId())
                .map(Post::delete)
                .orElseThrow(RuntimeException::new);

        return postPort.save(deleted);
    }
}
