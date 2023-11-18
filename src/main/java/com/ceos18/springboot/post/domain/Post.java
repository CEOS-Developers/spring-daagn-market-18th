package com.ceos18.springboot.post.domain;

import com.ceos18.springboot.global.common.BaseEntity;
import com.ceos18.springboot.member.domain.Member;
import com.ceos18.springboot.town.domain.Town;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(nullable = false)
    private Boolean activated;

    @Builder
    public Post(String title, String content, Long cost, Boolean sharing, Status status,
                Member member, Town town, Category category) {
        this.title = title;
        this.content = content;
        this.cost = cost;
        this.sharing = sharing;
        this.status = status;
        this.member = member;
        this.town = town;
        this.category = category;
        this.activated = true;
    }

    public void updateActivatedToFalse() {
        this.activated = false;
    }
}
