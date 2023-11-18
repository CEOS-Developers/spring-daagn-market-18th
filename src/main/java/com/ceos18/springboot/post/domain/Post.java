package com.ceos18.springboot.post.domain;

import com.ceos18.springboot.global.common.BaseEntity;
import com.ceos18.springboot.member.domain.Member;
import com.ceos18.springboot.town.domain.Town;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private Long cost;

    @Column(nullable = false)
    private Boolean sharing;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "town_id")
    private Town town;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;
}
