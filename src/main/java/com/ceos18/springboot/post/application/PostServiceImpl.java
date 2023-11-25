package com.ceos18.springboot.post.application;

import com.ceos18.springboot.member.domain.Member;
import com.ceos18.springboot.member.exception.MemberErrorCode;
import com.ceos18.springboot.member.exception.MemberException;
import com.ceos18.springboot.member.repository.MemberRepository;
import com.ceos18.springboot.post.domain.Category;
import com.ceos18.springboot.post.domain.Post;
import com.ceos18.springboot.post.dto.request.CreatePostRequest;
import com.ceos18.springboot.post.dto.response.PostResponse;
import com.ceos18.springboot.post.exception.PostErrorCode;
import com.ceos18.springboot.post.exception.PostException;
import com.ceos18.springboot.post.repository.PostRepository;
import com.ceos18.springboot.town.domain.Town;
import com.ceos18.springboot.town.exception.TownErrorCode;
import com.ceos18.springboot.town.exception.TownException;
import com.ceos18.springboot.town.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final TownRepository townRepository;

    @Override
    public void createPost(CreatePostRequest createPostRequest) {

        Member member = memberRepository.findByIdAndActivated(createPostRequest.getMemberId(), true)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND, createPostRequest.getMemberId()));

        Town town;
        if (townRepository.existsByName(createPostRequest.getTownName())) {
            town = townRepository.findByName(createPostRequest.getTownName())
                    .orElseThrow(() -> new TownException(TownErrorCode.TOWN_NOT_FOUND));
        } else {
            town = Town.builder()
                    .name(createPostRequest.getTownName())
                    .latitude(createPostRequest.getLatitude())
                    .longitude(createPostRequest.getLongitude())
                    .build();

            townRepository.save(town);
        }

        Category category = Category.getCategoryByName(createPostRequest.getCategory());

        postRepository.save(createPostRequest.toEntity(member, town, category));
    }

    @Override
    public PostResponse getPost(Long id) {

        Post post = postRepository.findByIdAndActivated(id, true)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND, id));

        return PostResponse.fromEntity(post);
    }

    @Override
    public List<PostResponse> getAllPosts() {

        List<PostResponse> postResponses = postRepository.findAll()
                .stream()
                .map(PostResponse::fromEntity)
                .toList();

        return postResponses;
    }

    @Override
    public void deletePost(Long id) {

        Post post = postRepository.findByIdAndActivated(id, true)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND, id));

        post.updateActivatedToFalse();
    }
}
