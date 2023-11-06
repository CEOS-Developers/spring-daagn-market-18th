package com.ceos18.springboot.controller;

import com.ceos18.springboot.dto.request.PostCreateRequestDto;
import com.ceos18.springboot.dto.request.PostUpdateRequestDto;
import com.ceos18.springboot.dto.response.PostReadResponseDto;
import com.ceos18.springboot.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostApiController {
	private final PostService postService;

	// 등록
	@PostMapping("/v1/post")
	public Long createPost(@RequestBody PostCreateRequestDto requestDto) {

		// TODO : 나중에 헤더의 토큰에서 member id 뽑아오는 로직 필요
		Long memberId = 1L; // 지금은 임시로 member id를 1 로 설정

		return postService.createPost(requestDto, memberId);
	}

	// 전체 조회
	@GetMapping("/v1/post/")
	public List<PostReadResponseDto> findAllPosts() {
		return postService.findAllPosts();
	}

	// 단건 조회
	@GetMapping("/v1/post/{postId}")
	public PostReadResponseDto findPost(@PathVariable("postId") Long postId) {
		return postService.findPost(postId);
	}

	// 수정
	@PatchMapping("/v1/post/{postId}")
	public Long updatePost(@PathVariable("postId") Long postId, @RequestBody PostUpdateRequestDto requestDto) {

		Long memberId = 1L; // 지금은 임시로 member id를 1 로 설정

		return postService.updatePost(requestDto, postId, memberId);
	}

	//삭제
	@DeleteMapping("/v1/post/{postId}")
	public Long deletePost(@PathVariable("postId") Long postId) {

		Long memberId = 1L; // 지금은 임시로 member id를 1 로 설정

		return postService.deletePost(postId, memberId);
	}

}
