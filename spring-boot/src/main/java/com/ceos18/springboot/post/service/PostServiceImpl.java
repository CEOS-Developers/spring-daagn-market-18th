package com.ceos18.springboot.post.service;

import com.ceos18.springboot.post.domain.Post;
import com.ceos18.springboot.post.dto.request.PostRequestDto;
import com.ceos18.springboot.post.dto.response.PostResponseDto;
import com.ceos18.springboot.post.exception.PostNotFoundException;
import com.ceos18.springboot.post.repository.PostCategoryRepository;
import com.ceos18.springboot.post.repository.PostImgRepository;
import com.ceos18.springboot.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostImgRepository postImgRepository;
    private final PostCategoryRepository postCategoryRepository;

    @Override
    @Transactional
    public void createPost(PostRequestDto postRequestDto) {
        Post post = postRequestDto.toEntity();
        postRepository.save(post);
    }

    @Override
    @Transactional
    public List<PostResponseDto> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(PostResponseDto::from).toList();
    }

    @Override
    @Transactional
    public PostResponseDto getPost(Long id) {
            return PostResponseDto.from(postRepository.findById(id)
                    .orElseThrow(PostNotFoundException::new));
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
    }
}
