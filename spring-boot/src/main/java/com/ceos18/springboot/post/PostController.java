package com.ceos18.springboot.post;


import com.ceos18.springboot.post.dto.request.PostRequestDto;
import com.ceos18.springboot.post.dto.response.PostResponseDto;
import com.ceos18.springboot.post.service.PostService;
import com.ceos18.springboot.post.service.PostServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/posts")
public class PostController {

    private final PostService postService;
    //@RequiredArgsConstructor를 사용했기 때문에 의존성이 자동 주입된다

    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody @Valid PostRequestDto postRequestDto) {
        log.info("상품 게시글 생성하기");
        postService.createPost(postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        log.info("모든 상품 게시글 조회하기");
        List<PostResponseDto> PostResponseList = postService.getAllPosts();
        return ResponseEntity.ok(PostResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        log.info("상품 게시글 상세 조회하기");
        PostResponseDto postResponse = postService.getPost(id);
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        log.info("상품 게시글 삭제하기");
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

}
