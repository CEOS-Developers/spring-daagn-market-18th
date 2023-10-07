package com.ceos18.springboot.post.service;

import com.ceos18.springboot.post.dto.request.PostRequestDto;
import com.ceos18.springboot.post.dto.response.PostResponseDto;

import java.util.List;

public interface PostService {
    void createPost(PostRequestDto postRequestDto);

    List<PostResponseDto> getAllPosts();

    PostResponseDto getPost(Long id);

    void deletePost(Long id);
}
