package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.fastcampus.kafkahandson.ugc.PostConverter.*;

@Component
@RequiredArgsConstructor
public class PostAdapter implements PostPort {

    private final PostJpaRepository postJpaRepository;
    @Override
    public Post save(Post post) {
        PostJpaEntity saved = postJpaRepository.save(toJpaEntity(post));
        return toModel(saved);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postJpaRepository.findById(id)
                .map(PostConverter::toModel);
    }
}
