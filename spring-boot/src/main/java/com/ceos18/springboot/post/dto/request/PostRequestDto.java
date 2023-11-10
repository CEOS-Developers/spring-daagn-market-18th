package com.ceos18.springboot.post.dto.request;

import com.ceos18.springboot.user.domain.User;
import com.ceos18.springboot.post.domain.Post;
import com.ceos18.springboot.post.domain.PostCategory;
import com.ceos18.springboot.post.domain.PostStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostRequestDto {

    private String title;
    private Long price;
    private String content;
    private PostCategory category;
    private User user;

    @Builder
    public PostRequestDto(String title, Long price, String content, PostCategory category, User user) {
        this.title = title;
        this.price = price;
        this.content = content;
        this.category = category;
        this.user = user;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .price(price)
                .content(content)
                .status(PostStatus.SALE)
                .category(category)
                .user(user)
                .build();
    }
}
