package com.daagn.clonestudy.post.dto.request;

import com.daagn.clonestudy.post.domain.Post;
import lombok.Getter;

@Getter
public class PostUpdateRequest {
    private String title;

    private Integer price;

    private Boolean isAuction;

    private String description;

    private String address;
}
