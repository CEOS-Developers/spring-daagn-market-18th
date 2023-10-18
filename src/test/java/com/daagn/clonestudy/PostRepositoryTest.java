package com.daagn.clonestudy;

import static org.assertj.core.api.Assertions.assertThat;

import com.daagn.clonestudy.member.domain.Member;
import com.daagn.clonestudy.member.domain.MemberRepository;
import com.daagn.clonestudy.post.domain.Post;
import com.daagn.clonestudy.post.domain.PostImage;
import com.daagn.clonestudy.post.domain.PostRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Slf4j
@DataJpaTest
class PostRepositoryTest {

  @Autowired
  private PostRepository postRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private TestEntityManager em;

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

  @Test
  @DisplayName("게시글 목록과 해당 게시글의 이미지를 조회한다.")
  void 게시글과_이미지_목록_조회하기(){
    // given
    Member 테스트유저1 = Member.builder().nickname("user1").town("서울시 마포구 대흥동").phoneNumber("00000000000").build();
    Member 테스트유저2 = Member.builder().nickname("테스트 유저2").town("서울시 마포구 대흥동").phoneNumber("00000000000").build();

    Post 게시글1 = Post.builder().title("테스트 제목1").price(15000).address("테스트 거래 주소1").isAuction(false).writer(테스트유저1).build();
    Post 게시글2 = Post.builder().title("테스트 제목2").price(15000).address("테스트 거래 주소2").isAuction(false).writer(테스트유저1).build();
    Post 게시글3 = Post.builder().title("테스트 제목3").price(15000).address("테스트 거래 주소3").isAuction(false).writer(테스트유저2).build();
    Post 게시글4 = Post.builder().title("테스트 제목4").price(15000).address("테스트 거래 주소4").isAuction(false).writer(테스트유저2).build();

    PostImage 게시글1_이미지1 = PostImage.builder().post(게시글1).imageUrl("게시글1_이미지1").isThumbnail(true).build();
    PostImage 게시글1_이미지2 = PostImage.builder().post(게시글1).imageUrl("게시글1_이미지2").isThumbnail(false).build();
    PostImage 게시글1_이미지3 = PostImage.builder().post(게시글1).imageUrl("게시글1_이미지3").isThumbnail(false).build();
    PostImage 게시글2_이미지1 = PostImage.builder().post(게시글2).imageUrl("게시글2_이미지1").isThumbnail(true).build();
    PostImage 게시글2_이미지2 = PostImage.builder().post(게시글2).imageUrl("게시글2_이미지2").isThumbnail(false).build();
    PostImage 게시글3_이미지1 = PostImage.builder().post(게시글3).imageUrl("게시글3_이미지1").isThumbnail(true).build();

    em.persist(테스트유저1);
    em.persist(테스트유저2);
    em.persist(게시글1);
    em.persist(게시글2);
    em.persist(게시글3).getId();
    Long 마지막_게시글_ID = em.persist(게시글4).getId();

    em.persist(게시글1_이미지1);
    em.persist(게시글1_이미지2);
    em.persist(게시글1_이미지3);
    em.persist(게시글2_이미지1);
    em.persist(게시글2_이미지2);
    em.persist(게시글3_이미지1);

    // when
    Pageable 페이징정보 = PageRequest.of(0, 20);
    List<Object[]> 조회결과 = postRepository.findAllWithImage(마지막_게시글_ID+1, 페이징정보).getContent();

    // then
    for(Object[] object: 조회결과){
      Post post = (Post)object[0];
      PostImage image = (PostImage)object[1];
      log.info("post = {}", post.getTitle());
      if(image == null)
        log.info("image = {}", image);
      else
        log.info("image = {}", image.getImageUrl());
    }
  }

}