package com.ceos18.springboot.service;

import com.ceos18.springboot.entity.Category;
import com.ceos18.springboot.entity.Member;
import com.ceos18.springboot.entity.Post;
import com.ceos18.springboot.entity.enums.PostStatus;
import com.ceos18.springboot.dto.post.request.PostCreateRequestDto;
import com.ceos18.springboot.dto.post.request.PostUpdateRequestDto;
import com.ceos18.springboot.dto.post.response.PostReadResponseDto;
import com.ceos18.springboot.repository.CategoryRepository;
import com.ceos18.springboot.repository.MemberRepository;
import com.ceos18.springboot.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final CategoryRepository categoryRepository;
	private final MemberRepository memberRepository;
//	private final ModelMapper modelMapper;

	/**
	 * 게시글 등록
	 */
	@Transactional
	public Long createPost(PostCreateRequestDto requestDto, Long memberId) {

		Category category = categoryRepository.findById(requestDto.getCategoryId())
				.orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다. id=" + requestDto.getCategoryId()));

		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id=" + memberId));


		Post post = Post.builder()
				.category(category)
				.seller(member)
				.title(requestDto.getTitle())
				.dealType(requestDto.getDealType())
				.description(requestDto.getDescription())
				.dealPlace(requestDto.getDealPlace())
				.price(requestDto.getPrice())
				.isPriceOffer(requestDto.getIsPriceOffer())
				.status(PostStatus.SALE)
				.likedCount(0)
				.viewCount(0)
				.build();

		return postRepository.save(post).getId();
	}

	/**
	 * 게시글 전체 조회
	 */
	public List<PostReadResponseDto> findAllPosts() {
		List<Post> posts = postRepository.findAll();
		return posts.stream()
				.map(PostReadResponseDto::fromEntity)
				.collect(Collectors.toList());
	}

	/**
	 * 게시글 단건 조회
	 */
	public PostReadResponseDto findPost(Long postId) {
		Post post =  postRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));


		// return modelMapper.map(post, PostReadResponseDto.class);
		return PostReadResponseDto.fromEntity(post);
	}


	/**
	 * 게시글 수정
	 */
	@Transactional
	public Long updatePost(PostUpdateRequestDto requestDto, Long postId, Long memberId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));

		// 게시글 작성자와 수정 요청자가 같은지 확인
		if (post.getSeller().getId() != memberId) {
			throw new IllegalArgumentException("게시글 작성자와 수정 요청자가 같지 않습니다.");
		}

		// requestDto.getCategoryId 가 있을 때
		Category category = null;
		if (requestDto.getCategoryId() != null) {
			category = categoryRepository.findById(requestDto.getCategoryId())
					.orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다. id=" + requestDto.getCategoryId()));
		}

		post.setTitle(requestDto.getTitle());

		if (category != null) {
			post.setCategory(category);
		}

		post.setDealType(requestDto.getDealType());
		post.setDescription(requestDto.getDescription());
		post.setDealPlace(requestDto.getDealPlace());
		post.setPrice(requestDto.getPrice());
		post.setIsPriceOffer(requestDto.getIsPriceOffer());

		return postId;
	}

	/**
	 * 게시글 삭제
	 */
	@Transactional
	public Long deletePost(Long postId, Long memberId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));

		// 게시글 작성자와 삭제 요청자가 같은지 확인
		if (post.getSeller().getId() != memberId) {
			throw new IllegalArgumentException("게시글 작성자와 삭제 요청자가 같지 않습니다.");
		}

		postRepository.delete(post);
		return postId;
	}



}
