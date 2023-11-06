package com.carrot.clonecoding.user.domain.model;


import com.carrot.clonecoding.achievement.domain.model.Achievement;
import com.carrot.clonecoding.common.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name",unique = true,nullable = false)
    private String nickName;
    @Column(nullable = false)
    private String password;

    @Column(name="profile_url")
    private String profileUrl;

    @Column(nullable = false)
    private double temperature;

    @Column(name="retrading_rate", nullable = false)
    private double retradingRate;

    @Column(name="response_rate", nullable = false)
    private double responseRate;

    @OneToMany(mappedBy="user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) //영속성 전이
    private List<Achievement> achievements;

    @Column(name = "email")
    private String email;

    @Column(nullable=false)
    private String phonenum;

    public void passwordEncode(BCryptPasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
