package com.ceos18.dangn.post.controller;

import com.ceos18.dangn.post.dto.PostListResponseDto;
import com.ceos18.dangn.post.dto.PostDetailResponseDto;
import com.ceos18.dangn.post.dto.RegisterPostRequestDto;
import com.ceos18.dangn.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> registerPost(@RequestBody RegisterPostRequestDto requestDto) {
        postService.registerPost(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public PostListResponseDto getPostList(@RequestParam(name = "pageNo", defaultValue = "1", required = true) int pageNo) {
        Sort sort = Sort.by(Sort.Direction.ASC, "modifiedAt");
        Pageable pageable = PageRequest.of(pageNo, 9, sort);
        return postService.getPostList(pageable);
    }

    @GetMapping("/{postId}")
    public PostDetailResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}
