package com.daagn.clonestudy.post.service;

import com.daagn.clonestudy.member.domain.Member;
import com.daagn.clonestudy.member.domain.MemberRepository;
import com.daagn.clonestudy.post.domain.Post;
import com.daagn.clonestudy.post.domain.PostImage;
import com.daagn.clonestudy.post.domain.PostRepository;
import com.daagn.clonestudy.post.domain.PostImageRepository;
import com.daagn.clonestudy.post.dto.request.PostCreateRequest;
import com.daagn.clonestudy.post.dto.response.PostListResponse;
import com.daagn.clonestudy.post.dto.response.PostResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

  private final String localImagePath = "/Users/jongmi/IdeaProjects/ceos-daagn-market/images";
  private final PostRepository postRepository;
  private final MemberRepository memberRepository;
  private final PostImageRepository postImageRepository;

  public PostResponse save(Member member, List<MultipartFile> images, PostCreateRequest request) throws IOException {
    Post post = postRepository.save(request.toEntity(member));

    boolean isFirst = true;
    for(MultipartFile image: images){
      if(image != null && image.isEmpty()){
        // 우선 로컬에 저장
        String fullPath = localImagePath + image.getOriginalFilename();
        image.transferTo(new File(fullPath));

        Boolean isThumbnail = false;
        if(isFirst) {
          isThumbnail = true;
          isFirst = false;
        }

        PostImage postImage = PostImage.builder()
            .post(post)
            .imageUrl(fullPath)
            .isThumbnail(isThumbnail)
            .build();

        PostImage saved = postImageRepository.save(postImage);
      }
    }

    return PostResponse.fromEntity(post);
  }

  public List<PostListResponse> listAll(Long lastId, int size){
    if(lastId == null) {
      Optional<Post> lastPost = postRepository.findTopByOrderByIdDesc();
      if(lastPost.isPresent())
        lastId = lastPost.get().getId() + 1L;
    }

    PageRequest pageRequest = PageRequest.of(0, size);
    List<Post> postList = postRepository.findAllByIdLessThanOrderByIdDesc(lastId, pageRequest).getContent();

    List<PostListResponse> responses = new ArrayList<>();
    for(Post post: postList){
      String image = null;
      Optional<PostImage> postImageOptional = postImageRepository.findFirstByPost(post);
      if (postImageOptional.isPresent()) {
        PostImage postImage = postImageOptional.get();
        image = postImage.getImageUrl();
      }
      responses.add(PostListResponse.fromEntity(post, image));
    }

    return responses;
  }

  public PostResponse detail(Long postId) throws Exception {
    Post post = postRepository.findById(postId).orElseThrow(()->new NotFoundException());
    return PostResponse.fromEntity(post);
  }

  public void delete(Long postId) throws Exception {
    Post post = postRepository.findById(postId).orElseThrow(()->new NotFoundException());
    postRepository.delete(post);
  }

}
