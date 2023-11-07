package practice.daangn.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String phone_number;
    @NotBlank
    private String name;
    @NotBlank
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
