package com.ceos18.springboot.post.dto.response;

import com.ceos18.springboot.post.domain.Post;
import com.ceos18.springboot.post.domain.PostImg;
import com.ceos18.springboot.post.domain.PostStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private Long price;
    private String content;
    private PostStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String categoryName;
    private String userNick;
    private List<PostImg> projectImages = new ArrayList<>();

    @Builder // Entity to Dto
    public PostResponseDto(Long id, String title, Long price, String content, PostStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, String categoryName, String userNick) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.categoryName = categoryName;
        this.userNick = userNick;
//        this.projectImages = projectImages;
    }

    public static PostResponseDto from(Post post){
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .price(post.getPrice())
                .content(post.getContent())
                .status(post.getStatus())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .categoryName(post.getCategory().getName())
                .userNick(post.getUser().getNick())
//                .projectImages(post.getProjectImages())
                .build();
    }
}
