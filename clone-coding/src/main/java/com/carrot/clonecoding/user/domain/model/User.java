package com.carrot.clonecoding.user.domain.model;


import com.carrot.clonecoding.achievement.domain.model.Achievement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true,nullable = false)
    private String nickName;
    @Column(nullable = false)
    private String password;

    private String profileUrl;

    @Column(nullable = false)
    private double temperature;

    @Column(nullable = false)
    private double retradingRate;

    @Column(nullable = false)
    private double responseRate;

    @OneToMany(mappedBy="user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) //영속성 전이
    private List<Achievement> achievements;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
