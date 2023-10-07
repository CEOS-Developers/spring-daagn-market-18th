package com.daagn.clonestudy.post.presentation;

import com.daagn.clonestudy.member.domain.Member;
import com.daagn.clonestudy.post.dto.request.PostCreateRequest;
import com.daagn.clonestudy.post.dto.response.PostResponse;
import com.daagn.clonestudy.post.service.PostService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @PostMapping("/post")
  public ResponseEntity<PostResponse> postSave(@RequestPart final PostCreateRequest request,
                                            @RequestPart List<MultipartFile> images,
                                            @AuthenticationPrincipal final Member authenticatedMember) throws IOException {
    return ResponseEntity.status(HttpStatus.OK).body(postService.save(authenticatedMember, images, request));
  }

}
