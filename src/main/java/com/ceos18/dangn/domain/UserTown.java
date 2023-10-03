package com.ceos18.dangn.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTown extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_town_id")
    private Long id;

    @ColumnDefault("2")
    private int townRange;

    private LocalDateTime townAuthTime;

    @Column(columnDefinition = "TINYINT(0) DEFAULT 0")
    private boolean isTownAuth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "town_id")
    private Town town;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserTown(User user, Town town) {
        this.user = user;
        this.town = town;
    }
}
