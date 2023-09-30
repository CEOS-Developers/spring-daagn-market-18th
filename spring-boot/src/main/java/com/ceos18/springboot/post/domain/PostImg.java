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
    private Long img_id;

    @NotNull
    private String img_url;

    @ManyToOne(fetch = FetchType.LAZY) // Cascade
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
