package com.daagn.clonestudy.post.dto.request;

import com.daagn.clonestudy.member.domain.Member;
import com.daagn.clonestudy.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

  private String title;

  private Integer price;

  private Boolean isAuction;

  private String description;

  private String address;

  public Post toEntity(Member writer){
    return Post.builder()
        .title(title)
        .price(price)
        .isAuction(isAuction)
        .description(description)
        .address(address)
        .writer(writer)
        .build();
  }

}
