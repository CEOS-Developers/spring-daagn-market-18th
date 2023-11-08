package practice.daangn.global;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import practice.daangn.domain.User;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private final User user;

    // CustomUserDetails 생성자에서 User 객체를 받아 UserDetails를 구성합니다.
    public CustomUserDetails(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = AuthorityUtils.createAuthorityList(user.getRole().toString());
        this.accountNonExpired = true; // 혹은 user 객체의 상태를 기반으로 설정
        this.accountNonLocked = true; // 혹은 user 객체의 상태를 기반으로 설정
        this.credentialsNonExpired = true; // 혹은 user 객체의 상태를 기반으로 설정
        this.enabled = true; // 혹은 user 객체의 상태를 기반으로 설정
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() { return password; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }
}
