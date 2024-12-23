package com.fastcampus.kafkahandson.ugc.post.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResolvedPost { // Post + 메타 정보

    private Post post;
    private String userName;
    private String categoryName;
    private boolean updated;

    public static ResolvedPost generate(Post post, String userName, String categoryName) {
        return new ResolvedPost(
                post,
                userName,
                categoryName,
                !post.getCreatedAt().equals(post.getUpdatedAt())
        );
    }

    public Long getId() {
        return post.getId();
    }

    public String getTitle() {
        return post.getTitle();
    }

    public String getContent() {
        return post.getContent();
    }

    public Long getUserId() {
        return post.getUserId();
    }

    public Long getCategoryId() {
        return post.getCategoryId();
    }

    public LocalDateTime getCreatedAt() {
        return post.getCreatedAt();
    }

    public LocalDateTime getUpdatedAt() {
        return post.getUpdatedAt();
    }

    public LocalDateTime getDeletedAt() {
        return post.getDeletedAt();
    }

}
