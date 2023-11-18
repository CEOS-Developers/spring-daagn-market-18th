package com.ceos18.springboot.post.dto.response;

import com.ceos18.springboot.post.domain.Category;
import com.ceos18.springboot.post.domain.Post;
import com.ceos18.springboot.post.domain.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

    private final Long postId;
    private final String title;
    private final Long cost;
    private final Boolean sharing;
    private final Status status;
    private final Long memberId;
    private final String memberNickname;
    private final String townName;
    private final Category category;

    @Builder
    public PostResponse(Long postId, String title, Long cost, Boolean sharing, Status status, Long memberId,
                        String memberNickname, String townName, Category category) {
        this.postId = postId;
        this.title = title;
        this.cost = cost;
        this.sharing = sharing;
        this.status = status;
        this.memberId = memberId;
        this.memberNickname = memberNickname;
        this.townName = townName;
        this.category = category;
    }

    public static PostResponse fromEntity(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .cost(post.getCost())
                .sharing(post.getSharing())
                .status(post.getStatus())
                .memberId(post.getMember().getId())
                .memberNickname(post.getMember().getNickname())
                .townName(post.getTown().getName())
                .category(post.getCategory())
                .build();
    }
}
