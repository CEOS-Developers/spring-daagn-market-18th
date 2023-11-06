package com.daagn.clonestudy.post.dto.response;

import com.daagn.clonestudy.member.domain.Member;
import com.daagn.clonestudy.post.domain.Post;
import com.daagn.clonestudy.post.domain.PostStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostResponse {

  private Long id;

  private String title;

  private Integer price;

  private Boolean isAuction;

  private String description;

  private String address;

  private PostStatus status;

  private Member writer;

  public static PostResponse fromEntity(Post post){
    return PostResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        .price(post.getPrice())
        .isAuction(post.getIsAuction())
        .description(post.getDescription())
        .address(post.getAddress())
        .status(post.getStatus())
        .writer(post.getWriter())
        .build();
  }

}
