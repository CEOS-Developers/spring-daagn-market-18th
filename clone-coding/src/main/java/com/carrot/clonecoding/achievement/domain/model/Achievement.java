package com.carrot.clonecoding.achievement.domain.model;

import com.carrot.clonecoding.common.base.BaseTimeEntity;
import com.carrot.clonecoding.user.domain.model.User;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "achievement")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Achievement extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "achievement_num", nullable = false)
    private int achievementNum;

}