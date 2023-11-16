package com.ceos18.dangn.post.service;

import com.ceos18.dangn.domain.*;
import com.ceos18.dangn.post.dto.PostDto;
import com.ceos18.dangn.post.dto.PostListResponseDto;
import com.ceos18.dangn.post.dto.PostDetailResponseDto;
import com.ceos18.dangn.post.dto.RegisterPostRequestDto;
import com.ceos18.dangn.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserTownRepository userTownRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    public Long registerPost(RegisterPostRequestDto requestDto, User user) {
        //로그인된 유저의 올바른 정보가 넘어온다고 가정
        Post post = requestDto.toEntity(user);

        TradeMethod tradeMethod = TradeMethod.valueOf(requestDto.getTradeMethod());
        post.setTradeMethod(tradeMethod);

        Category category = categoryRepository.findByName(requestDto.getCategory());
        post.setCategory(category);

        postRepository.save(post);

        return post.getId();
    }

    @Transactional(readOnly = true)
    public PostListResponseDto getPostList(Pageable pageable) {
        Page<Post> findPosts = postRepository.findByIsDel(false, pageable);

        Page<PostDto> postDtos = findPosts.map(post -> new PostDto(post,
                chatRoomRepository.getTotalChatRoom(post),
                userTownRepository.findByUser(post.getSeller()).get(0).getTown().getTownName()));
        //편의상 첫 번째 주소로 가정

        return new PostListResponseDto(postDtos.getTotalPages(), postDtos.getNumber(), postDtos.getContent());
    }

    public PostDetailResponseDto getPost(Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isPresent() && !findPost.get().isDel()) {
            //조회수 올려주기!
            postRepository.updateView(postId);

            Post post = findPost.get();

            //편의상 첫 번째 주소로 가정..
            String sellerTown = userTownRepository.findByUser(post.getSeller()).get(0).getTown().getTownName();

            return new PostDetailResponseDto(postId, post, sellerTown, chatRoomRepository.getTotalChatRoom(post));
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 게시물 요청");
        }
    }

    public void deletePost(Long postId) {
        postRepository.deletePost(postId);
    }
}
