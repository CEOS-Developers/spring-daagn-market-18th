package practice.daangn.user.dto;

import lombok.Builder;
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

    @Builder
    private UserResponseDto(String email, String password, String phone_number, String name, String nickname){
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.name = name;
        this.nickname = nickname;
    }

    public static UserResponseDto from(User user){
        return UserResponseDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .phone_number(user.getPhone_number())
                .name(user.getName())
                .nickname(user.getNickname())
                .build();
    }
}
