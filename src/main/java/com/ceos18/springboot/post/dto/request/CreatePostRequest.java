package com.ceos18.springboot.post.dto.request;

import com.ceos18.springboot.member.domain.Member;
import com.ceos18.springboot.post.domain.Category;
import com.ceos18.springboot.post.domain.Post;
import com.ceos18.springboot.post.domain.Status;
import com.ceos18.springboot.town.domain.Town;
import lombok.Getter;

@Getter
public class CreatePostRequest {

    private String title;
    private String content;
    private Long cost;
    private Boolean sharing;
    private Long memberId;
    private String townName;
    private Double latitude;
    private Double longitude;
    private String category;

    public Post toEntity(Member member, Town town, Category category) {
        return Post.builder()
                .title(title)
                .content(content)
                .cost(cost)
                .sharing(sharing)
                .status(Status.SELLING)
                .member(member)
                .town(town)
                .category(category)
                .build();
    }
}
