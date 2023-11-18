package com.ceos18.springboot.controller;

import com.ceos18.springboot.common.ApiResponse;
import com.ceos18.springboot.dto.post.request.PostCreateRequestDto;
import com.ceos18.springboot.dto.post.request.PostUpdateRequestDto;
import com.ceos18.springboot.dto.post.response.PostReadResponseDto;
import com.ceos18.springboot.security.authorize.UserAuthorize;
import com.ceos18.springboot.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@UserAuthorize
public class PostApiController {
	private final PostService postService;

	// 등록
	@PostMapping("/post")
	public ApiResponse<Long> createPost(@RequestBody PostCreateRequestDto requestDto) {

		// TODO : 나중에 헤더의 토큰에서 member id 뽑아오는 로직 필요
		Long memberId = 1L; // 지금은 임시로 member id를 1 로 설정

		return ApiResponse.createSuccess(postService.createPost(requestDto, memberId));
	}

	// 전체 조회
	@GetMapping("/post/")
	public ApiResponse<List<PostReadResponseDto>> findAllPosts() {
		return ApiResponse.createSuccess(postService.findAllPosts());
	}

	// 단건 조회
	@GetMapping("/post/{postId}")
	public ApiResponse<PostReadResponseDto> findPost(@PathVariable("postId") Long postId) {
		return ApiResponse.createSuccess(postService.findPost(postId));
	}

	// 수정
	@PatchMapping("/post/{postId}")
	public ApiResponse<Long> updatePost(@PathVariable("postId") Long postId, @RequestBody PostUpdateRequestDto requestDto) {

		Long memberId = 1L; // 지금은 임시로 member id를 1 로 설정

		return ApiResponse.createSuccess(postService.updatePost(requestDto, postId, memberId));
	}

	//삭제
	@DeleteMapping("/post/{postId}")
	public ApiResponse<Long> deletePost(@PathVariable("postId") Long postId) {

		Long memberId = 1L; // 지금은 임시로 member id를 1 로 설정

		return ApiResponse.createSuccess(postService.deletePost(postId, memberId));
	}

}
