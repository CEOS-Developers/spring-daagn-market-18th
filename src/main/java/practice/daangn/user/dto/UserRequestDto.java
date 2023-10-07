package practice.daangn.user.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.daangn.domain.Role;
import practice.daangn.domain.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String email;
    private String password;
    private String phone_number;
    private String name;
    private String nickname;

    @Builder
    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .phone_number(phone_number)
                .name(name)
                .nickname(nickname)
                .rating(36.5)
                .role(Role.ROLE_USER)
                .build();
    }

}
