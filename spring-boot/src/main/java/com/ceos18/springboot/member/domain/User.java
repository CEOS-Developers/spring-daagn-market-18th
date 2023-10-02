package com.ceos18.springboot.member.domain;

import com.ceos18.springboot.town.domain.Town;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "img_url")
    private String imgUrl;

    @NotNull
    @Column(name = "user_name")
    private String name;

    @NotNull
    @Column(name = "user_nick")
    private String nick;

    @NotNull
    private String pwd;

    @NotNull
    private String phone;

    private String email;

    @NotNull
    @ColumnDefault("36.5")
    private Double temperature;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "town_id", nullable = false)
    private Town town;

}
