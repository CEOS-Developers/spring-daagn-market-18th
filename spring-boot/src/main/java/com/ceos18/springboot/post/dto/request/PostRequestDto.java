package com.ceos18.springboot.post.dto.request;

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

@NoArgsConstructor
@Getter
public class PostRequestDto {

    private String title;
    private Long price;
    private String content;
    private PostStatus status;
    private PostCategory category;
    private User user;
    private List<PostImg> projectImages = new ArrayList<>();

    @Builder
    public PostRequestDto(String title, Long price, String content, PostStatus status, PostCategory category, User user, List<PostImg> projectImages) {
        this.title = title;
        this.price = price;
        this.content = content;
        this.status = status;
        this.category = category;
        this.user = user;
        this.projectImages = projectImages;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .price(price)
                .content(content)
                .status(status)
                .category(category)
                .user(user)
                .projectImages(projectImages)
                .build();
    }
}
