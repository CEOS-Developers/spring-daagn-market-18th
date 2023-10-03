package com.ceos18.springboot.post.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PostImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_img_id")
    private Long id;

    @NotNull
    @Column(name = "post_img_url")
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY) // Cascade
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
