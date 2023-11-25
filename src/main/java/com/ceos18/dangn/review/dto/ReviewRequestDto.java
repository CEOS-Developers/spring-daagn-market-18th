package com.ceos18.dangn.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    private Long postId;
    private String content;
    private String reviewType;
    private String reviewLevel;
}
