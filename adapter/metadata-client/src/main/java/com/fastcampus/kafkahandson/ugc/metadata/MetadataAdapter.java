package com.fastcampus.kafkahandson.ugc.metadata;

import com.fastcampus.kafkahandson.ugc.MetadataPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MetadataAdapter implements MetadataPort {

    private final MetadataClient metadataClient;

    @Override
    public String getCategoryNameByCategoryId(Long categoryId) {
        return metadataClient.getCategoryById(categoryId)
                .map(MetadataClient.CategoryResponse::name)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public String getUserNameByUserId(Long userId) {
        return metadataClient.getUserById(userId)
                .map(MetadataClient.UserResponse::name)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Long> listFollowerIdsByUserId(Long userId) {
        return metadataClient.getFollowerIdsByUserId(userId);
    }
}
