package goormton.backend.web1team.domain.User.dto.response;

public record JwtResponse(
        String accessToken,
        String tokenType
) {
}
