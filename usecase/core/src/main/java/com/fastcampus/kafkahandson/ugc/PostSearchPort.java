package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.inspectedpost.InspectedPost;

import java.util.List;

public interface PostSearchPort {
    void index(InspectedPost post);
    void delete(Long postId);
    List<Long> searchPostIdsByKeyword(String keyword, int pageNumber, int pageSize);
}
