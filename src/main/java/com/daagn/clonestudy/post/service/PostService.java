package com.daagn.clonestudy.post.service;

import com.daagn.clonestudy.member.domain.Member;
import com.daagn.clonestudy.member.domain.MemberRepository;
import com.daagn.clonestudy.post.domain.Post;
import com.daagn.clonestudy.post.domain.PostImage;
import com.daagn.clonestudy.post.domain.PostRepository;
import com.daagn.clonestudy.post.domain.PostImageRepository;
import com.daagn.clonestudy.post.dto.request.PostCreateRequest;
import com.daagn.clonestudy.post.dto.response.PostResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    for(MultipartFile image: images){
      if(image != null && image.isEmpty()){
        // 우선 로컬에 저장
        String fullPath = localImagePath + image.getOriginalFilename();
        image.transferTo(new File(fullPath));

        PostImage postImage = PostImage.builder()
            .post(post)
            .imageUrl(fullPath)
            .build();

        PostImage saved = postImageRepository.save(postImage);
      }
    }
    return PostResponse.fromEntity(post);
  }

}
