package com.daagn.clonestudy.post.presentation;

import com.daagn.clonestudy.member.domain.Member;
import com.daagn.clonestudy.post.dto.request.PostCreateRequest;
import com.daagn.clonestudy.post.dto.response.PostResponse;
import com.daagn.clonestudy.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @PostMapping("/post")
  public ResponseEntity<PostResponse> postSave(@RequestBody final PostCreateRequest request,
                                            @AuthenticationPrincipal final Member authenticatedMember){
    return ResponseEntity.status(HttpStatus.OK).body(postService.save(authenticatedMember, request));
  }

}
