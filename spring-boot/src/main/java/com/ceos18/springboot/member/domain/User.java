package com.ceos18.springboot.member.domain;

import com.ceos18.springboot.global.common.entity.BaseEntity;
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
public class User extends BaseEntity {

    @PrePersist
    public void prePresist(){
        this.temperature = this.temperature == null? 36.5:this.temperature;
    }

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
    private Double temperature;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "town_id") // nullable=false
    private Town town;

}
