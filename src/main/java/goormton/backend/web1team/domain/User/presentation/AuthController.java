package goormton.backend.web1team.domain.User.presentation;

import goormton.backend.web1team.domain.User.dto.request.LoginRequest;
import goormton.backend.web1team.domain.User.dto.request.SignupRequest;
import goormton.backend.web1team.domain.User.dto.response.JwtResponse;
import goormton.backend.web1team.domain.User.service.UserService;
import goormton.backend.web1team.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "권한 인증 API", description = "유저 로그인 및 회원가입에 대한 API입니다.")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final UserService userService;

    @Operation(summary = "회원가입 후 로그인", description = "회원가입과 로그인을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/signup")
    public ResponseCustom<?> signupUser(@Valid @RequestBody SignupRequest request) {
        JwtResponse response = userService.signupAndLogin(request);
        return ResponseCustom.CREATED(response);
    }

    @Operation(summary = "유저 로그인", description = "로그인을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/signup")
    public ResponseCustom<?> loginUser(@Valid @RequestBody LoginRequest request) {
        JwtResponse response = userService.loginAndGetToken(request);
        return ResponseCustom.OK(response);
    }
}
