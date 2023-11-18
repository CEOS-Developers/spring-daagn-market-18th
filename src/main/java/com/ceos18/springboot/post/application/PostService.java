package com.ceos18.springboot.post.application;

import com.ceos18.springboot.post.dto.request.CreatePostRequest;
import com.ceos18.springboot.post.dto.response.PostResponse;

import java.util.List;

public interface PostService {

    void createPost(CreatePostRequest createPostRequest);

    PostResponse getPost(Long id);

    List<PostResponse> getAllPosts();

    void deletePost(Long id);
}
