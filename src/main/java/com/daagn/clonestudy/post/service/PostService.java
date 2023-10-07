package com.daagn.clonestudy.post.service;

import com.daagn.clonestudy.member.domain.Member;
import com.daagn.clonestudy.member.domain.MemberRepository;
import com.daagn.clonestudy.post.domain.Post;
import com.daagn.clonestudy.post.domain.PostRepository;
import com.daagn.clonestudy.post.dto.request.PostCreateRequest;
import com.daagn.clonestudy.post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

  private final PostRepository postRepository;
  private final MemberRepository memberRepository;

  public PostResponse save(Member member, PostCreateRequest request){
    Post post = postRepository.save(request.toEntity(member));
    log.info("post id: {}", post);
    log.info("member id: {}", member);
    return PostResponse.fromEntity(post);
  }

}
