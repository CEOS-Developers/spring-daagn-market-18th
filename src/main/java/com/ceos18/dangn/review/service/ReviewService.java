package com.ceos18.dangn.review.service;

import com.ceos18.dangn.domain.*;
import com.ceos18.dangn.repository.PostRepository;
import com.ceos18.dangn.repository.ReviewRepository;
import com.ceos18.dangn.review.dto.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private PostRepository postRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ResponseEntity<Void> createReview(ReviewRequestDto reviewRequestDto, User user) {
        Review review = new Review(reviewRequestDto, user);
        Post post = postRepository.findById(reviewRequestDto.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "찾을 수 없는 게시물"));
        review.setPost(post);
        review.setReviewee(post.getSeller());

        reviewRepository.save(review);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
