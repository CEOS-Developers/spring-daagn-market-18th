package com.ceos18.springboot.post.service;

import com.ceos18.springboot.post.domain.Post;
import com.ceos18.springboot.post.dto.request.PostRequestDto;
import com.ceos18.springboot.post.dto.response.PostResponseDto;
import com.ceos18.springboot.post.exception.PostNotFoundException;
import com.ceos18.springboot.post.exception.UnauthorizedUserException;
import com.ceos18.springboot.post.repository.PostRepository;
import com.ceos18.springboot.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void createPost(PostRequestDto request, User user) {
        Post post = request.toEntity(user);
        postRepository.save(post);
    }

    @Transactional
    public List<PostResponseDto> getAllPosts() {
        List<Post> postList = postRepository.findByDeletedAtIsNull();
        return postList.stream().map(post -> PostResponseDto.from(post)).toList();
    }

    @Transactional
    public PostResponseDto getPost(Long id) {
            return PostResponseDto.from(postRepository.findByIdAndDeletedAtIsNull(id)
                    .orElseThrow(() -> new PostNotFoundException()));
    }

    @Transactional
    public void deletePost(Long id, User user) {
        Post post = postRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new PostNotFoundException());

        System.out.println("작성자임:"+post.getUser().getId());
        System.out.println(user.getId());

        if (Objects.equals(post.getUser().getId(), user.getId())) {
            post.deletePost(); // soft delete
        } else {
            throw new UnauthorizedUserException();
        }
    }
}
