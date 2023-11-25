package com.ceos18.dangn.review.controller;

import com.ceos18.dangn.domain.User;
import com.ceos18.dangn.review.dto.ReviewRequestDto;
import com.ceos18.dangn.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<Void> createReview(@RequestBody ReviewRequestDto reviewRequestDto, @AuthenticationPrincipal User user) {
        return reviewService.createReview(reviewRequestDto, user);
    }
}
