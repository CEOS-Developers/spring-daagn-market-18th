package com.ceos18.springboot.post.service;

import com.ceos18.springboot.member.domain.User;
import com.ceos18.springboot.post.domain.Post;
import com.ceos18.springboot.post.dto.request.PostRequestDto;
import com.ceos18.springboot.post.dto.response.PostResponseDto;
import com.ceos18.springboot.post.exception.PostNotFoundException;
import com.ceos18.springboot.post.repository.PostCategoryRepository;
import com.ceos18.springboot.post.repository.PostImgRepository;
import com.ceos18.springboot.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void createPost(PostRequestDto request) {
        Post post = request.toEntity();
        postRepository.save(post);
    }

    @Transactional
    public List<PostResponseDto> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(post -> PostResponseDto.from(post)).toList();
    }

    @Transactional
    public PostResponseDto getPost(Long id) {
            return PostResponseDto.from(postRepository.findById(id)
                    .orElseThrow(() -> new PostNotFoundException()));
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException());
        postRepository.delete(post);
    }
}
