package goormton.backend.web1team.domain.User.dto.response;

import goormton.backend.web1team.domain.User.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저 정보 조회용 responseDTO입니다.")
public record UserResponse(
        @Schema(description = "유저 객체 PK")
        Long userId,
        @Schema(description = "유저 아이디(로그인)")
        String username,
        @Schema(description = "유저 이메일")
        String email
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
