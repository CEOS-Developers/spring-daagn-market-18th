package com.daagn.clonestudy.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.daagn.clonestudy.common.service.ServiceTest;
import com.daagn.clonestudy.member.domain.Member;
import com.daagn.clonestudy.post.domain.Post;
import com.daagn.clonestudy.post.domain.PostImage;
import com.daagn.clonestudy.post.domain.PostImageRepository;
import com.daagn.clonestudy.post.domain.PostRepository;
import com.daagn.clonestudy.post.dto.request.PostCreateRequest;
import com.daagn.clonestudy.post.dto.response.PostListResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
class PostServiceTest extends ServiceTest {

  @Autowired
  private PostService postService;
  @Autowired
  private PostRepository postRepository;
  @Autowired
  private PostImageRepository postImageRepository;

  @Test
  @DisplayName("게시물을 생성한다.")
  public void 게시물_생성하기() throws IOException{
    // given
    Member 작성자 = 유저_등록하기("테스트유저", "마포구 노고산동", "00000000000");
    PostCreateRequest 게시물_생성_요청 = PostCreateRequest.builder()
        .title("에어팟 팝니다.")
        .price(10000)
        .isAuction(false)
        .description("싸게 팝니다.")
        .address("마포구 대흥동")
        .build();
    MultipartFile 게시물_이미지 = 가짜이미지_가져오기("test_image");
    List<MultipartFile> 게시물_이미지들 = new ArrayList<>();
    게시물_이미지들.add(게시물_이미지);

    // when
    postService.save(작성자, 게시물_이미지들, 게시물_생성_요청);

    // then
    Post 생성된_게시글 = postRepository.findAll().get(0);
    assertEquals(게시물_생성_요청.getTitle(), 생성된_게시글.getTitle());
  }

  @Test
  @DisplayName("게시물의 목록을 무한 스크롤로 조회한다.")
  public void 게시물목록_조회하기(){
    // given
    Member 작성자 = 유저_등록하기("테스트유저", "마포구 노고산동", "00000000000");
    Post 게시물1 = 게시물_등록하기("AAA", 3000, false, "BBB", "CCC", 작성자);
    Post 게시물2 = 게시물_등록하기("DDD", 3000, false, "EEE", "FFF", 작성자);
    Post 게시물3 = 게시물_등록하기("GGG", 3000, false, "HHH", "III", 작성자);
    Post 게시물4 = 게시물_등록하기("JJJ", 3000, false, "KKK", "LLL", 작성자);
    Post 게시물5 = 게시물_등록하기("MMM", 3000, false, "NNN", "OOO", 작성자);

    // when
    List<PostListResponse> 첫번째_스크롤 = postService.listAll(null, 2);
    List<PostListResponse> 두번째_스크롤 = postService.listAll(게시물4.getId(), 2);

    // then
    assertThat(첫번째_스크롤).hasSize(2)
        .extracting(PostListResponse::getTitle)
        .contains("MMM", "JJJ");
    assertThat(두번째_스크롤).hasSize(2)
        .extracting(PostListResponse::getTitle)
        .contains("GGG", "DDD");
  }

  @Test
  @DisplayName("게시물의 목록을 조회할 때 대표이미지를 반환한다.")
  void 이미지와_게시물목록_조회하기(){
    // given
    Member 테스트유저1 = 유저_등록하기("테스트 유저1", "테스트 주소", "00000000000");
    Member 테스트유저2 = 유저_등록하기("테스트 유저2", "테스트 주소", "00000000000");

    Post 게시글1 = 게시물_등록하기("테스트 제목1", 15000, false, "설명", "테스트 거래 주소1", 테스트유저1);
    Post 게시글2 = 게시물_등록하기("테스트 제목2", 15000, false, "설명", "테스트 거래 주소2", 테스트유저1);
    Post 게시글3 = 게시물_등록하기("테스트 제목3", 15000, false, "설명", "테스트 거래 주소3", 테스트유저2);
    Post 게시글4 = 게시물_등록하기("테스트 제목4", 15000, false, "설명", "테스트 거래 주소4", 테스트유저2);

    PostImage 게시글1_이미지1 = 게시물이미지_등록하기(게시글1, "게시글1_이미지1", true);
    PostImage 게시글1_이미지2 = 게시물이미지_등록하기(게시글1, "게시글1_이미지2", false);
    PostImage 게시글1_이미지3 = 게시물이미지_등록하기(게시글1, "게시글1_이미지3", false);
    PostImage 게시글2_이미지1 = 게시물이미지_등록하기(게시글2, "게시글2_이미지1", true);
    PostImage 게시글2_이미지2 = 게시물이미지_등록하기(게시글2, "게시글2_이미지2", false);
    PostImage 게시글3_이미지1 = 게시물이미지_등록하기(게시글3, "게시글3_이미지1", true);

    // when
    List<PostListResponse> 조회결과 = postService.listAll(null, 10);

    // then
    assertThat(조회결과).hasSize(4)
        .extracting(PostListResponse::getImage)
        .contains("게시글1_이미지1", "게시글2_이미지1", "게시글3_이미지1", null);
  }

  private MultipartFile 가짜이미지_가져오기(String imageName) throws IOException {
    String filePath = new File("").getAbsolutePath() + "/src/test/images/" + imageName + ".HEIC";
    byte[] fileContent = Files.readAllBytes(Path.of(filePath));
    MultipartFile 이미지 = new MockMultipartFile(
        "images",
        imageName + ".HEIC",
        "image/heic",
        fileContent
    );
    return 이미지;
  }

  private Post 게시물_등록하기(String title, Integer price, Boolean isAuction, String description, String address, Member writer){
    return postRepository.save(Post.builder()
        .title(title)
        .price(price)
        .isAuction(isAuction)
        .description(description)
        .address(address)
        .writer(writer)
        .build());
  }

  private PostImage 게시물이미지_등록하기(Post post, String imageUrl, Boolean isThumbnail){
    return postImageRepository.save(PostImage.builder()
        .post(post)
        .imageUrl(imageUrl)
        .isThumbnail(isThumbnail)
        .build());
  }
}