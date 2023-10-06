package com.daagn.clonestudy;

import static org.assertj.core.api.Assertions.assertThat;

import com.daagn.clonestudy.member.domain.Member;
import com.daagn.clonestudy.member.domain.MemberRepository;
import com.daagn.clonestudy.post.domain.Post;
import com.daagn.clonestudy.post.domain.PostRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PostRepositoryTest {

  @Autowired
  private PostRepository postRepository;
  @Autowired
  private MemberRepository memberRepository;

  @Test
  @DisplayName("게시글을 생성하고 조회한다.")
  void 게시글_생성하고_조회하기(){
    // given
    Member 테스트유저1 = Member.builder().nickname("user1").town("서울시 마포구 대흥동").phoneNumber("00000000000").build();
    Member 테스트유저2 = Member.builder().nickname("테스트 유저2").town("서울시 마포구 대흥동").phoneNumber("00000000000").build();

    Post 게시글1 = Post.builder().title("테스트 제목1").price(15000).address("테스트 거래 주소1").isAuction(false).writer(테스트유저1).build();
    Post 게시글2 = Post.builder().title("테스트 제목2").price(15000).address("테스트 거래 주소2").isAuction(false).writer(테스트유저1).build();
    Post 게시글3 = Post.builder().title("테스트 제목3").price(15000).address("테스트 거래 주소3").isAuction(false).writer(테스트유저2).build();

    // when
    memberRepository.saveAll(List.of(테스트유저1,테스트유저2));
    postRepository.saveAll(List.of(게시글1, 게시글2, 게시글3));

    List<Post> 저장된_게시글들 = postRepository.findAll();

    // then
    assertThat(저장된_게시글들).hasSize(3)
        .extracting(Post::getTitle)
        .contains("테스트 제목1", "테스트 제목2", "테스트 제목3");
  }

}