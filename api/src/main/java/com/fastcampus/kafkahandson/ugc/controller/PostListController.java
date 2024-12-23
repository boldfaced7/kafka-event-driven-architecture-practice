package com.fastcampus.kafkahandson.ugc.controller;

import com.fastcampus.kafkahandson.ugc.model.PostListedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostListController {

    @GetMapping("/inboxes/{userId}")
    ResponseEntity<List<PostListedResponse>> getSubscribingPosts(
            @PathVariable Long userId
    ) {
        return ResponseEntity.internalServerError().build();
    }
    @GetMapping
    ResponseEntity<List<PostListedResponse>> searchPosts(
            @RequestParam("query") String query
    ) {
        return ResponseEntity.internalServerError().build();
    }
}
