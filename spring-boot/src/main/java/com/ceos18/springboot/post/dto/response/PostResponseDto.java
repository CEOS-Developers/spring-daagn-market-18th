package com.ceos18.springboot.post.dto.response;

import com.ceos18.springboot.member.domain.User;
import com.ceos18.springboot.post.domain.Post;
import com.ceos18.springboot.post.domain.PostCategory;
import com.ceos18.springboot.post.domain.PostImg;
import com.ceos18.springboot.post.domain.PostStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
    private PostCategory category;
    private User user;
    private List<PostImg> projectImages = new ArrayList<>();

    @Builder // Entity to Dto
    public PostResponseDto(Long id, String title, Long price, String content, PostStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, PostCategory category, User user, List<PostImg> projectImages) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
        this.user = user;
        this.projectImages = projectImages;
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
                .category(post.getCategory())
                .user(post.getUser())
                .projectImages(post.getProjectImages())
                .build();

    }
}
