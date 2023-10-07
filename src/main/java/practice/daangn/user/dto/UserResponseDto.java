package practice.daangn.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.daangn.domain.User;

@NoArgsConstructor
@Getter
public class UserResponseDto {
    private String email;
    private String password;
    private String phone_number;
    private String name;
    private String nickname;

    public UserResponseDto(User user){
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone_number = user.getPhone_number();
        this.name = user.getPhone_number();
        this.nickname = user.getNickname();
    }
}
