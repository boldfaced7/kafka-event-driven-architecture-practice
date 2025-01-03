package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.ResolvedPost;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResolvedPostCacheAdapter implements ResolvedPostCachePort {

    private static final String KEY_PREFIX = "resolved_post:v1:";
    private static final Long EXPIRE_SECONDS = 60L * 60L * 24L * 7L; // 7일

    private final RedisTemplate<String, String> redisTemplate;
    private final CustomObjectMapper objectMapper = new CustomObjectMapper();

    @Override
    public Optional<ResolvedPost> get(Long postId) {
        String jsonString = redisTemplate.opsForValue()
                .get(generateCacheKey(postId));

        return Optional.ofNullable(jsonString)
                .map(this::readPost);
    }

    @Override
    public List<ResolvedPost> getAll(List<Long> postIds) {
        List<String> keys = postIds.stream()
                .map(this::generateCacheKey)
                .toList();

        List<String> jsonStrings = redisTemplate.opsForValue().multiGet(keys);
        if (jsonStrings == null) {
            return List.of();
        }

        return jsonStrings.stream()
                .filter(Objects::nonNull)
                .map(this::readPost)
                .toList();
    }

    @Override
    public void set(ResolvedPost resolvedPost) {
        redisTemplate.opsForValue()
                .set(
                        generateCacheKey(resolvedPost.getId()),
                        writePostAsString(resolvedPost),
                        Duration.ofSeconds(EXPIRE_SECONDS)
                );
    }

    @Override
    public void delete(Long postId) {
        redisTemplate.delete(generateCacheKey(postId));
    }

    private String generateCacheKey(Long postId) {
        return KEY_PREFIX + postId;
    }

    private ResolvedPost readPost(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, ResolvedPost.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String writePostAsString(ResolvedPost resolvedPost) {
        try {
            return objectMapper.writeValueAsString(resolvedPost);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
