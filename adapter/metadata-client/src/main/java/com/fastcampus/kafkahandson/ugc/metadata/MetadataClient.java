package com.fastcampus.kafkahandson.ugc.metadata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MetadataClient {

    private final WebClient webClient;

    public Optional<CategoryResponse> getCategoryById(Long categoryId) {
        return webClient
                .get()
                .uri("/categories/" + categoryId)
                .retrieve()
                .bodyToMono(CategoryResponse.class)
                .blockOptional();
    }

    public Optional<UserResponse> getUserById(Long userId) {
        return webClient
                .get()
                .uri("/users/" + userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .blockOptional();
    }

    public List<Long> getFollowerIdsByUserId(Long userId) {
        return webClient
                .get()
                .uri("/followers?followingId=/" + userId)
                .retrieve()
                .bodyToFlux(Long.class)
                .collectList()
                .block();
    }

    public record CategoryResponse(
            Long id,
            String name
    ) {}

    public record UserResponse(
            Long id,
            String email,
            String name
    ) {}

    public record FollowerIdsResponse(
            List<Long> followerIds
    ) {}
}
