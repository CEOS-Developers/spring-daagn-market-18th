package com.ceos18.springboot.member.domain;

import com.ceos18.springboot.town.domain.Town;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
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
    private Long user_id;

    private String img_url;

    @NotNull
    private String user_name;

    @NotNull
    private String user_nick;

    @NotNull
    private String pwd;

    @NotNull
    private String phone;

    private String email;

    @NotNull
    private Double temp = 36.5;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "town_id", nullable = false)
    private Town town_id;

}
