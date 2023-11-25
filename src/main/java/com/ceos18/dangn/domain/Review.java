package com.ceos18.dangn.domain;

import com.ceos18.dangn.review.dto.ReviewRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private ReviewType reviewType;

    @Enumerated(EnumType.STRING)
    private ReviewLevel reviewLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id")
    @Setter
    private User reviewee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @Setter
    private Post post;

    @Builder
    public Review(ReviewRequestDto reviewRequestDto, User user) {
        this.content = reviewRequestDto.getContent();
        this.reviewType = ReviewType.valueOf(reviewRequestDto.getReviewType());
        this.reviewLevel = ReviewLevel.valueOf(reviewRequestDto.getReviewLevel());
        this.reviewer = user;
    }
}
