package com.daagn.clonestudy.post.dto.response;

import com.daagn.clonestudy.post.domain.Post;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostListResponse {

  private Long id;

  private String title;

  private Integer price;

  private String address;

  private String image;

  private Long createdFromNow;

  @Enumerated(EnumType.STRING)
  private TimeStatus timeStatus;

  public static PostListResponse fromEntity(Post post, String image){
    TimeStatus timeStatus;
    Long createdFromNow;

    Duration duration = Duration.between(post.getCreatedDate(), LocalDateTime.now());
    if(duration.toHours() >= 1){
      timeStatus = TimeStatus.HOUR;
      createdFromNow = duration.toHours();
    }
    else{
      timeStatus = TimeStatus.MIN;
      createdFromNow = duration.toMinutes();
    }

    return PostListResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        .price(post.getPrice())
        .address(post.getAddress())
        .image(image)
        .createdFromNow(createdFromNow)
        .timeStatus(timeStatus)
        .build();
  }
}
