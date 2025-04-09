package goormton.backend.web1team.domain.User.dto.response;

import goormton.backend.web1team.domain.User.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저 정보 조회용 responseDTO입니다.")
public record UserResponse(
        Long userId,
        String username,
        String email
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
