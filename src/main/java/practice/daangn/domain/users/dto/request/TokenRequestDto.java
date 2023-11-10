package practice.daangn.domain.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TokenRequestDto {
    @NotBlank
    private String refreshToken;
}