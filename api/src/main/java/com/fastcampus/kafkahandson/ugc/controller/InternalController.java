package com.fastcampus.kafkahandson.ugc.controller;

import com.fastcampus.kafkahandson.ugc.PostInspectUsecase;
import com.fastcampus.kafkahandson.ugc.inspectedpost.InspectedPost;
import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalController {

    private final PostInspectUsecase postInspectUsecase;

    @GetMapping
    ResponseEntity<InspectedPost> inspectionTest(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("categoryId") Long categoryId
    ) {
        return postInspectUsecase.inspect(
                Post.generate(
                        0L,
                        title,
                        content,
                        categoryId
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
