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
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private PostCategory category;
    private User user;
    private List<PostImg> projectImages = new ArrayList<>();

    @Builder // Entity to Dto
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.price = post.getPrice();
        this.content = post.getContent();
        this.status = post.getStatus();
        this.created_at = post.getCreated_at();
        this.updated_at = post.getUpdated_at();
        this.category = post.getCategory();
        this.user = post.getUser();
        this.projectImages = post.getProjectImages();
    }
}
