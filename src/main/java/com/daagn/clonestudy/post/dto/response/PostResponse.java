package com.daagn.clonestudy.post.dto.response;

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

  private WriterResponse writer;

  public static PostResponse fromEntity(Post post){
    WriterResponse writer = null;
    if(post.getWriter() != null) {
      writer = WriterResponse.fromEntity(post.getWriter());
    }
    return PostResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        .price(post.getPrice())
        .isAuction(post.getIsAuction())
        .description(post.getDescription())
        .address(post.getAddress())
        .status(post.getStatus())
        .writer(writer)
        .build();
  }

}
