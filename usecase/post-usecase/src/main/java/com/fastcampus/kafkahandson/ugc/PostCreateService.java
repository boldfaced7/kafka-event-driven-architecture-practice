package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCreateService implements PostCreateUsecase {

    private final PostPort postPort;

    @Override
    public Post create(Request request) {
        return postPort.save(
                Post.generate(
                        request.userId(),
                        request.title(),
                        request.content(),
                        request.categoryId()
                )
        );
    }
}
