package com.ceos18.dangn.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String phone;

    private String email;

    @NotNull
    private String nickname;

    @NotNull
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    private String profileImage;

//    매너온도 고민
    @ColumnDefault("36.5")
    private double manners;

    @Builder
    public User(String phone, String nickname, String password, Role role) {
        this.phone = phone;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    /**
     * UserDetails
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(role.name()));
        return auth;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        //계정이 만료됐는지 리턴 -> true는 완료되지 않음 의미
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //계정이 잠겨있는지 리턴 -> true는 잠기지 않음
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //비밀번호가 만료됐는지 리턴 -> true는 만료X 의미
        return true;
    }

    @Override
    public boolean isEnabled() {
        //계정이 활성화돼 있는지 리턴 -> true는 활성화 상태 의미
        return true;
    }
}
