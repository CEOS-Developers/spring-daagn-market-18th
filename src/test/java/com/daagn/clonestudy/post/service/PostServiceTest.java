package com.daagn.clonestudy.post.service;

import static org.junit.jupiter.api.Assertions.*;

import com.daagn.clonestudy.common.service.ServiceTest;
import com.daagn.clonestudy.member.domain.Member;
import com.daagn.clonestudy.post.domain.Post;
import com.daagn.clonestudy.post.domain.PostRepository;
import com.daagn.clonestudy.post.dto.request.PostCreateRequest;
import com.daagn.clonestudy.post.dto.response.PostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PostServiceTest extends ServiceTest {

  @Autowired
  private PostService postService;
  @Autowired
  private PostRepository postRepository;

  @Test
  @DisplayName("게시물을 생성한다.")
  public void 게시물_생성하기(){
    // given
    Member 작성자 = 유저_등록하기("테스트유저", "마포구 노고산동", "00000000000");
    PostCreateRequest 게시물_생성_요청 = PostCreateRequest.builder()
        .title("에어팟 팝니다.")
        .price(10000)
        .isAuction(false)
        .description("싸게 팝니다.")
        .address("마포구 대흥동")
        .build();

    // when
    PostResponse 응답 = postService.save(작성자, 게시물_생성_요청);

    // then
    Post 생성된_게시글 = postRepository.findById(응답.getId()).get();
    assertEquals(게시물_생성_요청.getTitle(), 생성된_게시글.getTitle());
  }

}